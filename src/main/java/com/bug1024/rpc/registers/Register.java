package com.bug1024.rpc.registers;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册中心
 * @author wangyu
 * @date 2018-04-13
 */
public class Register {

    public List<String> discover() {
        List<String> list = new ArrayList<String>();
        return list;
    }

    public boolean register(String interfaceName, String addresses) {
        return true;
    }
}
