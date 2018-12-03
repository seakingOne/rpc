package com.ynhuang.netty.config;

import com.ynhuang.netty.annotation.MyRpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 15:17
 * @Description: 获取所有带有当前MyRpcService注解的类
 */
@Component
@Slf4j
public class RpcAnnnotClass implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String, Object> handlerMap = new ConcurrentHashMap<String, Object>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            Map<String, Object> beansWithAnnotation = contextRefreshedEvent.getApplicationContext()
                    .getBeansWithAnnotation(MyRpcService.class);
            for(Object bean : beansWithAnnotation.values()){
                log.info("当前对象为: {}", bean);
                handlerMap.put(bean.getClass().getName(), bean);
            }
        }
    }

    public static Map<String, Object> getRpcAnnnotClass(){
        return handlerMap;
    }
}
