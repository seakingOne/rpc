package com.ynhuang.netty.proxy;

import com.ynhuang.netty.ClientHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: 018399
 * @Date: 2019/3/6 16:06
 * @Description:
 */
public class FactoryProxy {

    private ClientHandler clientHandler;

    public Object getProxy(final Class clazz) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //参数一：被代理对象的类加载器，固定写法
        //参数二：被代理对象实现的接口，固定写法
        //参数三：使用的是策略模式，固定写法,如何调用真实对象的方法
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {

            @Override
            //代理对象调用的任何方法都会触发此方法执行
            //参数一：代理对象本身的引用，一般不用
            //参数二：代理对象调用的方法
            //参数三：代理对象调用的方法的参数
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if(clientHandler == null){

                }

                //设置参数信息
                clientHandler.setId((Integer) args[0]);
                return executorService.submit(clientHandler).get();
            }
        });

        return obj;
    }

}
