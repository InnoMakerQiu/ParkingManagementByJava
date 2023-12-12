# 停车管理系统控制类介绍

### `FinancialManagement`

这个类采用列表储存数据，当系统启动时，会自动从指定数据文件中加载数据，当系统退出时，自动将数据导出到指定数据文件中。

1. **构造方法和初始化：**
   - 构造方法 `public FinancialManagement(File dataFilePath)` 用于初始化账单列表，并接收一个 `File` 类型参数表示数据文件的路径。
   - 账单列表通过 `List<Bill> bills` 进行存储。
2. **数据加载与保存：**
   - `loadBillsFromFile()` 方法从文件加载账单数据，使用 `BufferedReader` 读取文件内容，并将每行转换为 `Bill` 对象。
   - `saveBillsToFile()` 方法将账单列表保存到文件，使用 `BufferedWriter` 写入每个 `Bill` 对象的字符串表示形式。
3. **账单操作：**
   - `addBill(Bill bill)` 方法用于将账单添加到账单列表。
   - `getYearlyRecords(String licensePlate, int year)` 方法根据车牌号和年份查询一年内的消费记录。
   - `getMonthlyRecords(String licensePlate, int year, int month)` 方法根据车牌号、年份和月份查询一个月内的消费记录。
   - `getTotalRevenueForMonthRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay)` 方法生成指定月份范围内的总车辆入库营收。
4. **日期处理和辅助方法：**
   - `calendarToDate(int year, int month, int day)` 方法将年、月、日转换为整数形式，例如yyyyMMdd。
   - `isWithinDayRange(int targetDate, int startDate, int endDate)` 方法判断目标日期是否在指定的时间范围内。
5. **关闭财务管理：**
   - `closeFinancialManagement()` 方法用于保存账单列表到文件，通常在程序结束时调用。



### `NetServiceToClient`

`NetServiceToClientSide` 类是一个多线程服务器，通过实现 `Runnable` 接口来处理与客户端的通信。它能够接收来自客户端的不同类型的请求，并调用相应的处理方法，实现了与车辆管理、财务管理和外部设备通信的功能。

1. **成员变量：**
   - `int port`: 服务器监听的端口号。
   - `ParkingManagement vehicleProcessor`: 处理车辆的 `ParkingManagement` 实例。
   - `ServerSocket serverSocket`: 服务器套接字。
   - `SQLBasedFinancialManagement financialManager`: 管理财务记录的 `FinancialManagement` 实例。
   - `NetServiceToPeripheralDevice netWork`: 与外部设备通信的 `NetServiceToPeripheralDevice` 实例。
   - `boolean flag`: 控制服务器运行状态的标志。

2. **构造方法：**
   - 构造方法接收端口号、车辆处理实例、财务管理实例和外部设备通信实例作为参数，并初始化成员变量。
   - 创建服务器套接字并输出启动信息。

3. **停止服务器：**
   - `stop()` 方法用于停止服务器，设置 `flag` 为 `false`，并调用外部设备通信和财务管理的关闭方法。

4. **Run 方法实现：**
   - `run()` 方法是 `Runnable` 接口的实现，通过循环等待客户端连接，并调用 `handleClient` 处理连接。

5. **处理客户端通信：**
   - `handleClient(Socket clientSocket)` 方法用于处理与客户端的通信，创建 `BufferedReader` 和 `PrintWriter` 进行读写。
   - 循环读取客户端消息，并通过 `processMessage` 方法处理。

6. **处理消息：**
   - `processMessage(String message, PrintWriter writer)` 方法处理从客户端接收到的消息，解析消息格式为 "Type:data"。
   - 根据消息类型调用相应的处理方法，如查询入库时间、查询总营收、查询车辆营收、查询停车场信息等。
   - 处理密码验证、关闭停车系统等特殊消息。

7. **消息处理细节：**
   - 解析请求并调用相应的方法处理，如查询车辆入库时间、查询总营收、查询车辆营收、查询停车场信息等。
   - 对于密码验证，检查账号和密码是否匹配。
   - 对于关闭停车系统的消息，调用 `stop` 方法停止服务器。

8. **辅助方法：**
   - `convertStringArrayToIntArray(String[] stringArray)` 方法将字符串数组转换为整数数组。



### `NetServiceToPeripheralDevice`

`NetServiceToPeripheralDevice` 类是一个实现了 `Runnable` 接口的服务器类，用于创建与外部设备通信的服务器。以下是对该类的总`NetServiceToPeripheralDevice` 类是一个多线程服务器，通过实现 `Runnable` 接口来处理与外部设备的通信。它能够接收来自外部设备的不同类型的请求，例如车辆的进入和离开，并通过调用相应的车辆管理和财务管理的方法来实现相应的功能。该类实现了基本的服务器功能，等待外部设备的连接，接收消息并作出响应。

1. **成员变量：**
   - `ServerSocket serverSocket`: 服务器套接字。
   - `int port`: 服务器监听的端口号。
   - `ParkingManagement vehicleProcessor`: 处理车辆的 `ParkingManagement` 实例。
   - `SQLBasedFinancialManagement financialManager`: 管理财务记录的 `FinancialManagement` 实例。
   - `boolean isRunning`: 控制服务器运行状态的标志。

2. **构造方法：**
   - 构造方法接收端口号、车辆处理实例、财务管理实例作为参数，并初始化成员变量。
   - 创建服务器套接字并输出启动信息。

3. **停止服务器：**
   - `stop()` 方法用于停止服务器，设置 `isRunning` 为 `false`。

4. **Run 方法实现：**
   - `run()` 方法是 `Runnable` 接口的实现，通过循环等待客户端连接，并调用 `handleClient` 处理连接。

5. **处理客户端通信：**
   - `handleClient(Socket clientSocket)` 方法用于处理与客户端的通信，创建 `BufferedReader` 和 `PrintWriter` 进行读写。
   - 循环读取客户端消息，并通过 `processMessage` 方法处理。

6. **处理消息：**
   - `processMessage(String message, PrintWriter writer)` 方法处理从客户端接收到的消息，假定消息格式为 "licensePlate,in/out"。
   - 解析消息格式，调用相应的处理方法，如处理车辆进入、离开等。
   - 向客户端发送相应的响应消息。



### `ParkingManagement`

该类实现了基本的停车场管理功能，包括车辆的停入和取出，费用的计算等。通过使用停车场和停车票的组合，提供了简单而有效的停车管理系统。

1. **成员变量：**
   - `ParkingLot parkingLot`: 停车场实例，用于管理停车位。
   - `List<ParkingTicket> activeTickets`: 存储当前停车中的车辆信息的列表。

2. **构造方法：**
   - 构造方法接收停车场的总停车位数，初始化了停车场实例和存储停车票的列表。

3. **停车场信息获取：**
   - `getParkingLot()`: 获取停车场实例。

4. **停车操作：**
   - `parkVehicle(String vehicle)`: 将车辆停入停车场，记录入场时间，生成停车票并添加到停车记录中。
   - 抛出 `ParkingException` 异常，如果停车场已满。

5. **取车操作：**
   - `exitParking(String vehicle)`: 将车辆从停车场取出，记录出场时间，计算费用，释放停车位，从停车记录中移除停车票。
   - 返回停车票信息。
   - 抛出 `ParkingException` 异常，如果未找到相关车辆信息。

6. **查找停车票：**
   - `findParkingTicketByLicensePlate(String licensePlate)`: 根据车牌号查找停车票。

7. **获取车辆入场时间：**
   - `getEntryTimeByLicensePlate(String licensePlate)`: 根据车牌号获取车辆入场时间。
   - 抛出 `ParkingException` 异常，如果未找到车辆有效信息。

8. **计算停车费用：**
   - `calculateParkingCost(ParkingTicket ticket)`: 根据停车票信息计算停车费用。
   - `calculateParkingCostByLicensePlate(String licensePlate)`: 根据车牌号计算停车费用。
   - 抛出 `ParkingException` 异常，如果未找到车辆信息。



### `SQLBasedFinancialManagement`

`SQLBasedFinancialManagement` 类是用于管理基于 SQL 的财务数据的类，以下是对该类的总结：

1. **成员变量：**
   - `Connection connection`: 数据库连接。

2. **构造方法：**
   - 构造方法用于初始化数据库连接，并创建账单表。

3. **数据库连接：**
   - `createDatabaseConnection()`: 创建数据库连接，使用 H2 数据库，并在内存中生成一个随机名称的数据库。
   - 抛出 `RuntimeException` 异常，如果创建数据库连接失败。

4. **创建账单表：**
   - `createBillTable()`: 创建账单表，包括车牌号、入场时间、出场时间、费用和日期等字段。

5. **添加账单到数据库：**
   - `addBill(Bill bill)`: 将账单信息插入数据库的 bills 表中。

6. **获取年度记录：**
   - `getYearlyRecords(String licensePlate, int year)`: 从数据库中获取指定车牌号和年份的年度记录。

7. **获取月度记录：**
   - `getMonthlyRecords(String licensePlate, int year, int month)`: 从数据库中获取指定车牌号、年份和月份的月度记录。

8. **计算总收入：**
   - `getTotalRevenueForMonthRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay)`: 根据指定日期范围计算总收入。

9. **从 ResultSet 中创建 Bill 对象：**
   - `createBillFromResultSet(ResultSet resultSet)`: 从数据库查询结果的 `ResultSet` 中创建 `Bill` 对象。

10. **关闭数据库连接：**
    - `closeFinancialManagement()`: 关闭数据库连接。

该类通过 JDBC（Java Database Connectivity）与 H2 数据库交互，实现了将账单信息存储到数据库中和从数据库中检索账单信息的功能。这种实现方式有助于数据的持久化存储和更高效的数据检索。