package com.bug1024.rpc.server;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对外暴露接口提供服务的注解
 * Server启动时会扫描该注解的接口并将其注册到注册中心
 * @author wangyu
 * @date 2018-04-13
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 接口
     */
    Class<?> value();

    /**
     * 版本号
     */
    String version() default "";
}
