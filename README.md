# jeet-rpc

A simple RPC framework based on Netty

## 组成
* Spring：使用Spring配置服务，加载Bean，扫描注解
* 通信：使用Netty作为通信框架
* 编解码：使用Protostuff序列化和反序列化消息
* 动态代理：客户端使用代理模式透明化服务调用
