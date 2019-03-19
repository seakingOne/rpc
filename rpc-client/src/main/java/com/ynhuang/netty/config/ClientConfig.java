//package com.ynhuang.netty.config;
//
//import com.ynhuang.netty.NettyClient;
//import com.ynhuang.netty.annotation.MyRpcClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @Auther: 018399
// * @Date: 2018/12/3 14:00
// * @Description:
// */
//@Component
//@Slf4j
//public class ClientConfig implements ApplicationListener<ContextRefreshedEvent> {
//
//    private static Map<String, Object> handlerMap = new ConcurrentHashMap<String, Object>();
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
//            try {
//                //1 netty客户端启动
//                //NettyClient.getServerInstance().startClient();
//
//                //2 获取到所以引用了rpc注解的对象
//                Map<String, Object> beansWithAnnotation = contextRefreshedEvent.getApplicationContext()
//                        .getBeansWithAnnotation(MyRpcClient.class);
//
//                for(Object bean : beansWithAnnotation.values()){
//                    log.info("当前对象为: {}", bean);
//                    handlerMap.put(bean.getClass().getName(), bean);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static Map<String, Object> getRpcAnnnotClass(){
//        return handlerMap;
//    }
//
//}
