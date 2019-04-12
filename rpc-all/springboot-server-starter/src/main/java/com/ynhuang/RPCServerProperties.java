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

    /** zk注册地址 **/
    private String registryAddress;

    /** 服务暴露地址 **/
    private String exportAddress;

    /** service的包路径 **/
    private String serviceBasePackage;
}
