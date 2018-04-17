package com.coder520.common.utils;

import com.coder520.common.constants.Parameters;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by JackWangon[www.coder520.com] 2018/4/4.
 */
@Component
public class ZkClient {

    @Autowired
    private Parameters parameters;

    @Bean
    public CuratorFramework getZkClient(){
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(parameters.getZkHost())
                .connectionTimeoutMs(3000)
                .retryPolicy(new RetryNTimes(5,500));
        CuratorFramework framework = builder.build();
        framework.start();
        return framework;
    }

}
