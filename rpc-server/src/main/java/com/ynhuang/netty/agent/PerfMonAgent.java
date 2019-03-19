package com.ynhuang.netty.agent;

import java.lang.instrument.Instrumentation;

/**
 * @Auther: 018399
 * @Date: 2019/3/19 16:13
 * @Description:
 *
 * 步骤二：编写agent类，需要实现premain方法
 *
 * 步骤三：打包agent，此处以idea中maven工程为例
 *
 * 具体操作步骤：https://blog.csdn.net/zh2508/article/details/86531452
 *
 */
public class PerfMonAgent {

    private static Instrumentation inst = null;

    public static void premain(String agentArgs, Instrumentation _inst){

        System.out.println("PerfMonAgent.premain() was called...");
        inst = _inst;
        System.out.println("Adding a PerfMonXformer instance to the JVM...");
        inst.addTransformer(new PerfMonXformer());

    }

}
