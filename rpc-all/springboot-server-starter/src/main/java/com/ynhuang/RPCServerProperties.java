package com.ynhuang;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ynhuang
 * @date 2019/4/11
 */
@ConfigurationProperties(prefix = "rpc")
@Data
public class RPCServerProperties {

    /** 注册地址 **/
    private String registryAddress;

    /** service的包路径 **/
    private String serviceBasePackage;
}
