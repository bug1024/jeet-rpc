package com.bug1024.rpc.registers;

import com.bug1024.rpc.constants.CommonConstant;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 服务注册中心
 * @author wangyu
 * @date 2018-04-13
 */
public class Discovery {

    private String zkAddress;
    private int zkSessionTime;
    private int zkConnectionTime;

    public Discovery(String zkAddress, int zkSessionTime, int zkConnectionTime) {
        this.zkAddress = zkAddress;
        this.zkSessionTime = zkSessionTime;
        this.zkConnectionTime = zkConnectionTime;
    }

    public String discover(String interfaceName) {
        ZkClient zkClient = new ZkClient(this.zkAddress, this.zkSessionTime, this.zkConnectionTime);
        try {
            String servicePath = CommonConstant.ZK_BASE_PATH + "/" + interfaceName;
            if (!zkClient.exists(servicePath)) {
                throw new RuntimeException("service: " + interfaceName + " can not find");
            }
            List<String> addressList = zkClient.getChildren(servicePath);
            if (CollectionUtils.isEmpty(addressList)) {
                throw new RuntimeException("service: " + interfaceName + " addresses can not find");
            }

            String address;
            int size = addressList.size();
            if (size == 1) {
                address = addressList.get(0);
            } else {
                address = addressList.get(ThreadLocalRandom.current().nextInt(size));
            }
            String addressPath = servicePath + "/" + address;
            return zkClient.readData(addressPath);
        } finally {
            zkClient.close();
        }
    }
}
