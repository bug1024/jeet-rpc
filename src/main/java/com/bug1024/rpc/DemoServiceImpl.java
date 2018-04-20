package com.bug1024.rpc;

import com.bug1024.rpc.server.RpcService;

/**
 * DemoServiceImpl
 * @author wangyu
 * @date 2018-04-19
 */
@RpcService(DemoService.class)
public class DemoServiceImpl implements DemoService {

    @Override
    public String say() {
        return "love you";
    }

    @Override
    public String say(String word) {
        return word;
    }
}
