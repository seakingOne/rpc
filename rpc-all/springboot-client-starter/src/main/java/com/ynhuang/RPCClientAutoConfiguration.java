package com.ynhuang;


import com.ynhuang.client.RPCClient;
import com.ynhuang.lb.LoadBalancer;
import com.ynhuang.lb.impl.ConsistentHashLoadBalancer;
import com.ynhuang.lb.impl.LeastActiveLoadBalancer;
import com.ynhuang.lb.impl.RandomLoadBalancer;
import com.ynhuang.lb.impl.RoundRobinLoadBalancer;
import com.ynhuang.proxy.RPCConsumerProxyFactoryBeanRegistry;
import com.ynhuang.util.PropertyUtil;
import com.ynhuang.zk.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ynhuang
 * @date 2018/3/10
 */
@Configuration
@EnableConfigurationProperties(RPCClientProperties.class)
@ConditionalOnMissingBean(RPCConsumerProxyFactoryBeanRegistry.class)
@Slf4j
public class RPCClientAutoConfiguration {

    /** 获取对应的配置信息 **/
    @Autowired
    private RPCClientProperties properties;

    @Autowired
    private ApplicationContext applicationContext;

    private static RPCClient CLIENT;

    /**
     * 一致性hash
     * @return
     */
    @Bean(name = "CONSISTENT_HASH")
    public ConsistentHashLoadBalancer consistentHashLoadBalancer() {
        return new ConsistentHashLoadBalancer();
    }

    /**
     * 随机
     * @return
     */
    @Bean(name = "RANDOM")
    public RandomLoadBalancer randomLoadBalancer() {
        return new RandomLoadBalancer();
    }

    /**
     * 轮询
     * @return
     */
    @Bean(name = "RR")
    public RoundRobinLoadBalancer roundRobinLoadBalancer() {
        return new RoundRobinLoadBalancer();
    }

    /**
     * 最少连接数
     * @return
     */
    @Bean(name = "LEAST_ACTIVE")
    public LeastActiveLoadBalancer leastActiveLoadBalancer() {
        return new LeastActiveLoadBalancer();
    }
    
    
    @Bean
    public RPCClient rpcClient() {
        log.info("properties:{}", properties);
        //获取具体的访问策略
        LoadBalancer loadBalancer = applicationContext.getBean(properties.getLoadBalanceStrategy().toUpperCase(), LoadBalancer.class);
        ServiceDiscovery discovery = new ServiceDiscovery(properties.getRegistryAddress(), loadBalancer);
        CLIENT.setDiscovery(discovery);
        CLIENT.init();
        return CLIENT;
    }

    /**
     * 因为RPCProxyFactoryBeanRegistry初始化是在常规bean还没有初始化之前进行的，所以是拿不到@Autowired的属性的
     * 只能去直接读配置文件才能得到basePackage
     *
     * @return
     */
    @Bean
    public static RPCConsumerProxyFactoryBeanRegistry rpcConsumerProxyFactoryBeanRegistry() {
        CLIENT = new RPCClient();
        String basePackage = PropertyUtil.getProperty("rpc.serviceBasePackage");
        return new RPCConsumerProxyFactoryBeanRegistry(CLIENT, basePackage);
    }
}
