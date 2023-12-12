# 停车管理系统服务端

### 目录结构

##### `controller`

这个文件夹目录下主要包含了一些控制类，有管理车辆进出的parkingManagement类，管理财务报告的SQLBasedFinancialManagement类，以及负责管理和边缘设备通信的NetServiceToPeripheralDevice类，以及负责和系统用户客户端进行通信的NetServiceToClient类

##### `model`

这个目录夹下面主要定义了记录车辆出入信息的ParkingTicket类，记录财务信息的Bill类以及记录VIP用户的相关类。



### 如何维护我们的代码

如果想要继续添加部分功能，请尽量只操作NetServiceToClient类，如果只修改NetService类不能满足你，请和我们的团队联系，我们将会竭力帮助你进行完善。
