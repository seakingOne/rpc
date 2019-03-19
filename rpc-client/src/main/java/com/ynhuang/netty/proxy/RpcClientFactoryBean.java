package com.ynhuang.netty.proxy;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Proxy;

/**
 * @Auther: 018399
 * @Date: 2019/3/19 10:07
 * @Description:
 */
@Data
public class RpcClientFactoryBean implements FactoryBean {

    @Autowired
    private RpcClientProxy rpcClientProxy;

    private Class<?> classType;

    public RpcClientFactoryBean(Class<?> classType) {
        this.classType = classType;
    }

    @Override
    public Object getObject() throws Exception {
        ClassLoader classLoader = classType.getClassLoader();
        Object object = Proxy.newProxyInstance(classLoader, new Class<?>[] { classType }, rpcClientProxy);
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.classType;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
