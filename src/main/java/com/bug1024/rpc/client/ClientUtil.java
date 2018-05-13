package com.bug1024.rpc.client;

import com.bug1024.rpc.DemoService;
import com.bug1024.rpc.constants.CommonConstant;
import com.bug1024.rpc.domain.RpcRequest;
import com.bug1024.rpc.domain.RpcResponse;
import com.bug1024.rpc.registers.Discovery;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 客户端请求工具
 * @author wangyu
 * @date 2018-04-20
 */
public class ClientUtil {

    private Discovery serviceDiscovery;

    public ClientUtil(Discovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T build(Class<?> interfaceClass, String serviceVersion) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId("trace-" + System.currentTimeMillis());
                        rpcRequest.setInterfaceName(method.getDeclaringClass().getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setVersion(serviceVersion);
                        rpcRequest.setArgs(args);
                        rpcRequest.setArgTypes(method.getParameterTypes());

                        String address = serviceDiscovery.discover(rpcRequest.getInterfaceName());
                        if (StringUtils.isEmpty(address)) {
                            throw new RuntimeException("discover failed");
                        }
                        String[] array = StringUtils.split(address, CommonConstant.HOST_PORT_SEPARATOR);
                        String host = array[0];
                        int port = Integer.valueOf(array[1]);
                        Client client = new Client(host, port);
                        RpcResponse rpcResponse = client.send(rpcRequest);

                        if (rpcResponse == null) {
                            throw new Exception("call error");
                        }
                        if (rpcResponse.getException() == null) {
                            return rpcResponse.getResult();
                        } else {
                            throw rpcResponse.getException();
                        }
                    }
                }
        );
    }
}
