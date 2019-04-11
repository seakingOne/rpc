package com.ynhuang.client.handler;

import com.ynhuang.client.RPCClient;
import com.ynhuang.domain.Message;
import com.ynhuang.domain.RPCResponse;
import com.ynhuang.domain.RPCResponseFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.ynhuang.domain.Message.PONG;
import static com.ynhuang.domain.Message.RESPONSE;

/**
 * Created by ynhuang on 2019/4/11.
 */
@Slf4j
@AllArgsConstructor
public class RPCClientHandler extends SimpleChannelInboundHandler<Message> {

    private RPCClient client;

    private Map<String, RPCResponseFuture> responses;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception { 
        log.info("客户端捕获到异常");
        cause.printStackTrace();
        log.info("与服务器的连接断开");
        client.handleException();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端通道已开启...");
    }

    /**
     * 当超过规定时间，客户端未读写数据，那么会自动调用userEventTriggered方法，向服务器发送一个心跳包
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("超过指定时间未发送数据，客户端主动发送心跳信息");
            ctx.writeAndFlush(Message.PING_MSG);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        log.info("接收到服务器响应: {}", message);
        //服务器不会PING客户端
        if (message.getType() == PONG) {
            log.info("收到服务器的PONG心跳响应");
        } else if (message.getType() == RESPONSE) {
            log.info("{}", message.getClass().getName());
            RPCResponse response = message.getResponse();
            if (responses.containsKey(response.getRequestId())) {
                RPCResponseFuture future = responses.remove(response.getRequestId());
                future.setResponse(response);
            }
        }
    }
}
