package com.ynhuang.netty;

import com.ynhuang.netty.domain.Request;
import com.ynhuang.netty.domain.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 13:56
 * @Description:
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler {

    private static Map<String, ChannelHandlerContext> channelHandlerContextMap
            = new ConcurrentHashMap<String, ChannelHandlerContext>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response str = (Response) msg;
        log.info("服务端发送给客户端的消息为: {}", str);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHandlerContextMap.put("newCtx", ctx);
    }

    public static void sendDataToServer(Request s) {
        ChannelHandlerContext newCtx = channelHandlerContextMap.get("newCtx");
        newCtx.channel().writeAndFlush(s);
    }
}
