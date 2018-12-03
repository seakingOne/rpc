package com.ynhuang.netty;

import com.ynhuang.netty.bean.UserInfo;
import com.ynhuang.netty.config.RpcAnnnotClass;
import com.ynhuang.netty.domain.Request;
import com.ynhuang.netty.domain.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 13:43
 * @Description:
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler {

    // 用于记录和管理所有客户端的channle
    public static ChannelGroup users =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Map<String, Object> rpcAnnnotClass = RpcAnnnotClass.getRpcAnnnotClass();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;
        log.info("服务端接收到客户端发来的消息为: {}", request);

        //遍历对外暴露的接口数据，和request传递过来的数据作对比
        UserInfo result = handlerInterfaces(request);

        //给客户端返回确认消息
        Response response = new Response();
        response.setResult(result);
        sendDataToClient(response);
    }

    /**
     * 从客户端传递过来的接口信息做查询处理
     * @param request
     */
    private UserInfo handlerInterfaces(Request request) {
        String className = request.getClassName();
        Object interfaceObject = rpcAnnnotClass.get(className);

        UserInfo result = null;
        if(interfaceObject != null){
            //获取类下的所有方法
            Method[] declaredMethods = interfaceObject.getClass().getDeclaredMethods();
            try {
                //比对当前调用的方法是否符合
                for (Method method : declaredMethods) {
                    if (method.getName().equals(request.getMethod())) {
                        result = (UserInfo) method.invoke(interfaceObject, request.getParams());
                        break;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    public void sendDataToClient(Response response) {
        users.writeAndFlush(response);
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("RpcAnnnotClass对象为: {}", RpcAnnnotClass.getRpcAnnnotClass());
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        String channelId = ctx.channel().id().asShortText();
        log.info("客户端被移除，channelId为：{}", channelId);

        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }

}
