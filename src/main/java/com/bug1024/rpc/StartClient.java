package com.bug1024.rpc;

import com.bug1024.rpc.client.ClientUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动客户端
 * @author wangyu
 * @date 2018-04-13
 */
public class StartClient {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-client.xml");
        ClientUtil clientUtil = (ClientUtil) context.getBean("clientUtil");
        DemoService demoService = clientUtil.build(DemoService.class, "");
        String word = demoService.say("folk you");
        System.out.println(word);

        TestService testService = clientUtil.build(TestService.class, "1.0.1");
        String test = testService.say();
        System.out.println(test);

        System.exit(0);
    }

}
