# rpc
手动实现一个rpc框架，zk作为注册中心
功能列表
基于Netty实现长连接式的RPC，包括心跳保持、断线重连、解决粘包半包等
基于Zookeeper实现分布式服务注册与发现，并实现了轮询、随机、加权随机、一致性哈希等负载均衡 算法，以及FailOver、FailFast、FailSafe等多种集群容错方式
参考Dubbo实现了分层结构，如 config,proxy,cluster,protocol,filter,invocation,registry,transport,executor,serialize等层
实现了同步、异步、回调、Oneway等多种调用方式
实现了客户端侧的Filter，并基于此实现了LeastActive负载均衡算法
实现了简易扩展点，泛化调用等功能
基于动态代理实现透明RPC，并为其编写了Spring Boot Starter
