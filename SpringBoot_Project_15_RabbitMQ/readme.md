# rabbitMQ的使用

## 基础介绍,消息队列的流派

一般分为2类  
- 有broker的mq：存在中间件broker，所有消息经过broker再转发至客户端，例如：
  - 重Topic：kafka、JMS  
    kafka传输效率块的原因：将大量消息队列压缩，再发送至客消费者进行解压；容易造成消息丢失
  - 轻Topic：RabbitMQ  生产者将消息存放至指定的key，消费者通过消息订阅来获取属于自己队列queue（数据）  
   可以实现负载均衡，队列大小取决于服务器的内存  
   此种模式下属于轻量级的topic，生产者只需关注消息存放  
   消费者只需关心自身的队列，用于映射key和queue的称为交换机（exchange）  

- 无broker的mq
  - 代表：ZeroMQ
    因为MQ解决的socket通讯问题，因此将broker设计为一个**库**而非中间件  
    "库"即是服务端，也是消费端，通过重量级的Actor模型来实现
    - 关于Actor模型
      Actor属于并行 异步消息模型，Actor模型需要满足操作系统对进程/线程的要求  
      最重要的一点是必须实现公平调度，然而java的akka是无法实现公平调度的，erLang可以  
      而RabbitMQ是通过ErLang语言实现的，因此本项目采用RabbitMQ作为消息队列  

## 关于RabbitMQ 

基于ErLang语言开发，适用于集群服务器(kafka则是分布式)  
支持多语言，跨平台有消息确认和持久化机制，可靠且开源    
RMQ可将消息设置为持久化、临时、或自动删除  
RMQ中的交换机exchange类似于数据通信中的交换机  

- 生产者在传递消息时会附带一个ROUTING_KEY,exchange根据key指定给专用的消费者(即路由器中arp协议)  
  (而arp伪装即伪装自己的ip地址欺骗交换机)

- 而RMQ Server会创建多各虚拟的Massage Broker(即VirtualHosts)
  也即小型的MQ Server,保证边界隔离相互之间不会干扰
- 因此生产者和消费者连接RMQ Server时,需要指定一个virtual host

RMQ类似于网络组网,总RMQServer即三层交换机,MiniRMQServer即下层的交换机,消费者则为最下层的各主机

## 创建RMQ服务器端 通过docker

通过`docker-compose.yml`创建

```yaml
# 注意修改用户名密码和容器位置即可
version: '3.1'
services:
  rabbitmq:
    restart: always
#    image: rabbitmq:management
    image: arm32v7/rabbitmq
    container_name: my-rabbitmq
    ports:
      - 5672:5672
      - 15672:15672
    environment:
      TZ: Asia/Shanghai
      RABBITMQ_DEFAULT_USER: rabbit
      RABBITMQ_DEFAULT_PASS: dhnB0v42aAVs
    volumes:
      - ./rabbitmq-data:/var/lib/rabbitmq
```

如果出现无法访问web控制页面(:15672),可以参考这里:  
https://blog.csdn.net/plg17/article/details/96316637

可能是新版的docker修改了配置,之前的版本还没遇到这事儿

## 使用rabbitMQ(基于spring提供的amqpTemplate)

### 主要依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### 创建过程

服务提供者部分:  
1. 创建配置类`RabbitConfiguration`,在其中创建队列
2. 创建服务提供者`RabbitProvider`,使用`amqpTemplate`发送至rabbitMQ服务器端
3. 创建配置文件yml,告知amqpTemplate服务器端的地址

本案例中用测试类进行消息发送

服务消费者部分:  
1. 创建`RabbitConsumer`,使用注解`@RabbitListener(queues = "hello-rabbit")`监听队列
2. 通过`@RabbitHandler` 创建方法，对监听的消息进行消费
