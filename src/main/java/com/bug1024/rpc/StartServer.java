package com.bug1024.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动服务端
 * @author wangyu
 * @date 2018-04-13
 */
public class StartServer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-server.xml");
    }
}
