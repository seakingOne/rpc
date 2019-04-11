package com.ynhuang.server.handler;

import com.ynhuang.domain.Message;
import com.ynhuang.domain.RPCRequest;
import com.ynhuang.domain.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by ynhuang on 2019/4/11.
 */
@Slf4j
public class Worker implements Runnable {
    private ChannelHandlerContext ctx;
    private RPCRequest request;
    private Map<String, Object> handlerMap;
    
    public Worker(ChannelHandlerContext ctx, RPCRequest request, Map<String, Object> handlerMap) {
        this.ctx = ctx;
        this.request = request;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        RPCResponse response = new RPCResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Throwable t) {
            t.printStackTrace();
            response.setCause(t);
        }
        log.info("服务器已调用完毕服务，结果为: {}", response);
        // 这里调用ctx的write方法并不是同步的，也是异步的，将该写入操作放入到pipeline中
        ctx.writeAndFlush(Message.buildResponse(response));
    }

    /**
     * 反射调用方法
     *
     * @param request
     * @return
     * @throws Throwable
     */
    private Object handle(RPCRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }
}
