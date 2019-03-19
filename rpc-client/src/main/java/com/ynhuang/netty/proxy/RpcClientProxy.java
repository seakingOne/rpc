package com.ynhuang.netty.proxy;

import com.ynhuang.netty.ClientHandler;
import com.ynhuang.netty.domain.Request;
import com.ynhuang.netty.domain.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Auther: 018399
 * @Date: 2019/3/19 10:08
 * @Description:
 */
@Slf4j
@Component
public class RpcClientProxy implements InvocationHandler {

    private static ClientHandler clientHandler;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //String requestJson = objectToJson(method, args);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //建立netty客户端并启动
        if(clientHandler == null){
            initNettyClient();
        }

        //组装请求参数
        Request request = new Request();

        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String className = method.getDeclaringClass().getName();

        request.setMethodName(methodName);
        request.setParameTypes(parameterTypes);
        request.setParameters(args);
        request.setClassName(getClassName(className));

        //clientHandler.sendDataToServer(request);
        clientHandler.setRequest(request);

        Future<Response> submit = executorService.submit(clientHandler);

        //获取输出结果
        Response response = submit.get();

        return response.getResult();
    }

    /**
     * 第一次使用需要创建一个netty client
     */
    private void initNettyClient() throws InterruptedException {
        clientHandler = new ClientHandler();
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(mainGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast(new ObjectEncoder());
                        pipeline.addLast(clientHandler);
                    }
                });

        ChannelFuture future = bootstrap.connect("127.0.0.1", 8086).sync();
        log.error("客户端启动成功......");
    }

//    public String objectToJson(Method method, Object[] args) {
//
//        Request request = new Request();
//
//        String methodName = method.getName();
//        Class<?>[] parameterTypes = method.getParameterTypes();
//        String className = method.getDeclaringClass().getName();
//
//        request.setMethodName(methodName);
//        request.setParameTypes(parameterTypes);
//        request.setParameters(args);
//        request.setClassName(getClassName(className));
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
//        Gson gson = gsonBuilder.create();
//        return gson.toJson(request);
//    }
//
    private String getClassName(String beanClassName) {
        String className = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        return className;
    }
}
