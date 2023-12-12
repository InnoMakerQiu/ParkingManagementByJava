# 停车场管理系统模拟

这个项目是一个停车场系统管理的模拟实现，分为客户端（ClientSide）和服务端（ParkingManagement）两个主要部分。

## 目录结构

### `ClientSide/`

- **Client:** 包含客户端 GUI 界面的相关函数，定义了与用户交互的操作。

- **DisplayInterface:** 定义了客户端 GUI 中与服务端交互的相关函数。

- **ParkingManageClient:** 模拟外围设备，实现向服务端发送数据请求的操作。

### `ParkingManagement/`

- **FinancialManagement:** 定义负责财务报表生成的相关类。

- **Main:** 启动整个服务端。

- **NetServiceToClientSide:** 与客户端进行网络通信的部分。

- **NetServiceToPeripheralDevice:** 负责与边缘网络设备通信的部分。

- **ParkingException:** 管理在库车辆的出库与入库，定义了非法停车相关的异常。

- **ParkingTicket:** 定义与停车相关的类。

- **VIPUserManagement:** 管理 VIP 用户相关的类。
- **SQLBasedFinancialManagement:** 通过数据库定义负责财务报表生成的相关类。

### 如何运行

1. 在 IntelliJ IDEA 中打开项目，并确保已配置 Java SDK。

2. 打开 `ParkingManagementSystem` 模块，找到 `Main` 类，右键单击该类，
选择 "Run Main.main()"启动服务端。

3. 打开 `ClientSide` 模块，找到 `Client` 和 `ParkingManageClient` 类，
右键单击每个类 ，选择 "Run" 启动客户端和外围设备模拟。


确保在运行之前已安装项目所需的依赖项，并根据需要进行配置。
需要配置选项：
1. 安装配置h2驱动数据库，这是参考连接：http://t.csdnimg.cn/naxdB

**注意:**

1. 本停车系统需要正确地关闭，首先，需要点击Client类中的gui界面中点击关闭停车系统选项 ，
  同时在ParkingManagementClient的终端命令中输入exit命令整个系统才会关闭。
