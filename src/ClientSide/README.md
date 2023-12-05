## Readme

#### 项目结构：

Client：这个是客户端gui界面的相关函数，定义了相关操作

DisplayInterface：这个定义了客户端gui中要与服务段交互的相关函数。

ParkingManageClient：这个主要是模拟外设备实现向服务

#### 注意事项：
尽量不要输入非法选项，
虽然我们代码较为健壮，
如果要测试非法选项，请在熟析代码之后进行
防止不必要的资源泄露。




#### 修改规范：
* try to change setDefaultCloseOperation to set it as DISPOSE_ON_CLOSE.
* naming should be standardized.
* annotations should be detailed.
* Try to write operations on the same object together as much as possible
* Try to place files and images in the same directory as much as possible.
* Create anonymous classes and use lambda expressions as much as possible
* A good habit of writing an interface is to first define the jFrame framework, define components and add components., define event listeners,
