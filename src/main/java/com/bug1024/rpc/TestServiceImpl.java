package com.bug1024.rpc;

import com.bug1024.rpc.server.RpcService;

/**
 * DemoServiceImpl
 * @author wangyu
 * @date 2018-04-19
 */
@RpcService(value = TestService.class, version = "1.0.1")
public class TestServiceImpl implements TestService {

    @Override
    public String say() {
        return "just test";
    }

    @Override
    public String say(String word) {
        return word;
    }
}
