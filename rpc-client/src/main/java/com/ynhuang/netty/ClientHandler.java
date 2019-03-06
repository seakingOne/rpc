package com.ynhuang.netty;

import com.ynhuang.netty.config.ClientConfig;
import com.ynhuang.netty.domain.Request;
import com.ynhuang.netty.domain.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 13:56
 * @Description:
 */
@Slf4j
@Data
public class ClientHandler extends SimpleChannelInboundHandler implements Callable<Response> {

    private static Map<String, ChannelHandlerContext> channelHandlerContextMap
            = new ConcurrentHashMap<String, ChannelHandlerContext>();

    private Map<String, Object> rpcAnnnotClass = ClientConfig.getRpcAnnnotClass();

    private Response result;
    private ChannelHandlerContext context;
    private int id;

    // TODO: 2019/3/6 这里采用了同步的方式等待返回结果
    
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response str = (Response) msg;
        result = str;
        log.info("服务端发送给客户端的消息为: {}", str);
        notify();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //channelHandlerContextMap.put("me", ctx);
        context = ctx;
    }

    public static void sendDataToServer(Request s) {
        ChannelHandlerContext newCtx = channelHandlerContextMap.get("me");
        newCtx.channel().writeAndFlush(s);
    }

    @Override
    public synchronized Response call() throws Exception {
        Request request = new Request();
        request.setClassName("com.ynhuang.netty.interfaces.impl.QueryUserByIdImpl");
        Object[] params = new Object[]{id};
        request.setParams(params);
        request.setMethod("queryUserById");
        context.writeAndFlush(request);
        wait();
        return result;
    }
}
