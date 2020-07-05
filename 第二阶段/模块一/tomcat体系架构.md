![image-20200705141608156](C:\Users\liquanlin\AppData\Roaming\Typora\typora-user-images\image-20200705141608156.png)

tomcat有两个⾮常重要的功能需要完成

1）和客户端浏览器进⾏交互，进⾏socket通信，将字节流和Request/Response等对象进⾏转换

2）Servlet容器处理业务逻辑

Tomcat 设计了两个核⼼组件连接器（Connector）和容器（Container）来完成 Tomcat 的两⼤核⼼

功能。

连接器，负责对外交流： 处理Socket连接，负责⽹络字节流与Request和Response对象的转化；

容器，负责内部处理：加载和管理Servlet，以及具体处理Request请求；



容器采用套娃式架构，如下图所示：

![image-20200705141113924](C:\Users\liquanlin\AppData\Roaming\Typora\typora-user-images\image-20200705141113924.png)

可以认为整个Tomcat就是⼀个Catalina实例，Tomcat 启动的时候会初始化这个实例，Catalina 实例通过加载server.xml完成其他实例的创建，创建并管理⼀个Server，Server创建并管理多个服务，

每个服务⼜可以有多个Connector和⼀个Container。

⼀个Catalina实例（容器）

⼀个 Server实例（容器）

多个Service实例（容器）

每⼀个Service实例下可以有多个Connector实例和⼀个Container实例



Catalina

负责解析Tomcat的配置⽂件（server.xml） , 以此来创建服务器Server组件并进⾏管理

Server

服务器表示整个Catalina Servlet容器以及其它组件，负责组装并启动Servlaet引擎,Tomcat连接

器。Server通过实现Lifecycle接⼝，提供了⼀种优雅的启动和关闭整个系统的⽅式

Service

服务是Server内部的组件，⼀个Server包含多个Service。它将若⼲个Connector组件绑定到⼀个

Container

Container

容器，负责处理⽤户的servlet请求，并返回对象给web⽤户的模块

4.3 Container 组件的具体结构



Container组件下有⼏种具体的组件，分别是Engine、Host、Context和Wrapper。这4种组件（容器）

是⽗⼦关系。Tomcat通过⼀种分层的架构，使得Servlet容器具有很好的灵活性。

Engine

表示整个Catalina的Servlet引擎，⽤来管理多个虚拟站点，⼀个Service最多只能有⼀个Engine，

但是⼀个引擎可包含多个Host

Host

代表⼀个虚拟主机，或者说⼀个站点，可以给Tomcat配置多个虚拟主机地址，⽽⼀个虚拟主机下

可包含多个Context

Context

表示⼀个Web应⽤程序， ⼀个Web应⽤可包含多个Wrapper

Wrapper

表示⼀个Servlet，Wrapper 作为容器中的最底层，不能包含⼦容器

上述组件的配置其实就体现在conf/server.xml中。