package com.ynhuang.netty.config;

import com.ynhuang.netty.NettyClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 14:00
 * @Description:
 */
@Component
public class ClientConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            try {
                NettyClient.getServerInstance().startClient();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
