package com.ynhuang;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ynhuang
 * @date 2018/3/10
 */
@ConfigurationProperties(prefix = "rpc")
@Data
public class RPCClientProperties {
    private String registryAddress;
    private String serviceBasePackage;
    private String loadBalanceStrategy;
}
