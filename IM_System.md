# 一.基本架构

![image-20231105111404518](IM_System.assets/image-20231105111404518.png)

![image-20231105111545304](IM_System.assets/image-20231105111545304.png)

### 1.接入层

1.与客户端进行TCP长连接,消息收发

3.与逻辑层rpc调用,mq

### 2.逻辑层

1.处理用户,好友,群组等业务逻辑

如:是否为好友或黑名单等,群组是否禁言,

### 3.存储层

数据持久化

