package com.bug1024.rpc.registers;

import com.bug1024.rpc.constants.CommonConstant;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务注册中心
 * @author wangyu
 * @date 2018-05-13
 */
public class Register {

    private String zkAddress;
    private int zkSessionTime;
    private int zkConnectionTime;

    private final ZkClient zkClient;

    public Register(String zkAddress, int zkSessionTime, int zkConnectionTime) {
        this.zkClient = new ZkClient(zkAddress, zkSessionTime, zkConnectionTime);
    }

    public void register(String interfaceName, String addresses) {
        String registryPath = CommonConstant.ZK_BASE_PATH;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
        }
        // service持久节点
        String servicePath = registryPath + "/" + interfaceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
        }

        // address临时节点
        String addressPath = servicePath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath, addresses);
    }
}
