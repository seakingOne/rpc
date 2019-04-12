package com.ynhuang.coder;

import com.ynhuang.domain.Message;
import com.ynhuang.domain.RPCRequest;
import com.ynhuang.domain.RPCResponse;
import com.ynhuang.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by ynhuang on 2017/7/30.
 */
@Slf4j
public class RPCDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        byte type = in.readByte();
        log.info("解码消息，消息类型为:{}", type);

        if (type == Message.PING) {
            out.add(Message.PING_MSG);
        } else if (type == Message.PONG) {
            out.add(Message.PONG_MSG);
        } else {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            if (type == Message.REQUEST) {
                out.add(Message.buildRequest(ProtostuffUtil.deserialize(bytes, RPCRequest.class)));
            } else if (type == Message.RESPONSE) {
                out.add(Message.buildResponse(ProtostuffUtil.deserialize(bytes, RPCResponse.class)));
            }
        }
    }

//    @Override
//    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
//        ByteBuf in = (ByteBuf) msg;
//        byte type = in.readByte();
//        if (type == Message.PING) {
//            log.info("接收到PING消息");
//            out.add(Message.PING_MSG);
//        } else if (type == Message.PONG) {
//            log.info("接收到PONG消息");
//            out.add(Message.PONG_MSG);
//        } else {
//            byte[] bytes = new byte[in.readableBytes()];
//            in.readBytes(bytes);
//            if (type == Message.REQUEST) {
//                log.info("接收到REQUEST消息");
//                out.add(ProtostuffUtil.deserialize(bytes, RPCRequest.class));
//            } else if (type == Message.RESPONSE) {
//                log.info("接收到RESPONSE消息");
//                out.add(ProtostuffUtil.deserialize(bytes, RPCResponse.class));
//            }
//        }
//    }

}
