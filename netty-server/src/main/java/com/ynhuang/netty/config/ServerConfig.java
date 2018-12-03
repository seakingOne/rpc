package com.ynhuang.netty.config;

import com.ynhuang.netty.NettyServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 13:47
 * @Description:
 */
@Component
public class ServerConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            try {
                NettyServer.getServerInstance().startServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
