package com.ynhuang.netty.config;

import com.ynhuang.netty.NettyServer;
import com.ynhuang.netty.annotation.MyRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 13:47
 * @Description:
 */
@Component
@Slf4j
public class ServerConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String, Object> handlerMap = new ConcurrentHashMap<String, Object>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            try {
                //1 启动netty服务
                NettyServer.getServerInstance().startServer();

                //2 获取到所以引用了rpc注解的对象
                Map<String, Object> beansWithAnnotation = contextRefreshedEvent.getApplicationContext()
                        .getBeansWithAnnotation(MyRpcService.class);
                for(Object bean : beansWithAnnotation.values()){
                    log.info("当前对象为: {}", bean);
                    handlerMap.put(bean.getClass().getName(), bean);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, Object> getRpcAnnnotClass(){
        return handlerMap;
    }
}
