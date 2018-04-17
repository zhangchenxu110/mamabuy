package com.coder520.common.constants;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 系统参数
 */
@Component
@Data
@RefreshScope
public class Parameters {

    /*****redis config start*******/
    @Value("${redis.node}")
    private String redisNode;
    /*****redis config end*******/

    /***zk config start ***/
    @Value("${zk.host}")
    private String zkHost;


}
