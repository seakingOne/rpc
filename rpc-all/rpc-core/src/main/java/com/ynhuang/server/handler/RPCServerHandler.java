package com.ynhuang.server.handler;

import com.ynhuang.domain.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.ynhuang.domain.Message.PING;
import static com.ynhuang.domain.Message.REQUEST;

/**
 * Created by ynhuang on 2019/4/11.
 * 实际的业务处理器，单例
 */
@Slf4j
public class RPCServerHandler extends SimpleChannelInboundHandler<Message> {

    private Map<String, Object> handlerMap;

    //自定义线程池
    private ThreadPoolExecutor pool;
    
    public RPCServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
        int threads = Runtime.getRuntime().availableProcessors();
        this.pool = new ThreadPoolExecutor( threads,
                                            threads,
                                6L,
                                            TimeUnit.SECONDS,
                                            new ArrayBlockingQueue<>(100),
                                            new ThreadPoolExecutor.CallerRunsPolicy());
        log.info("{}",handlerMap);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("接收到客户端的连接");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {

        log.info("服务器已接收请求 {}，请求类型 {}", message, message.getType());

        if (message.getType() == PING) {

            log.info("收到客户端PING心跳请求，发送PONG心跳响应");
            ctx.writeAndFlush(Message.PONG_MSG);

        } else if (message.getType() == REQUEST) {
            pool.submit(new Worker(ctx, message.getRequest(), handlerMap));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        try {
            cause.printStackTrace();
        } finally {
            ctx.close();
        }
    }
    
    /**
     * 当超过规定时间，服务器未读取到来自客户端的请求，则关闭连接
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("超过规定时间服务器仍未收到客户端的心跳或正常信息，关闭连接");
            ctx.close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
