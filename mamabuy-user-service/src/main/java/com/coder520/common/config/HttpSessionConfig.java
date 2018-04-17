package com.coder520.common.config;

import com.coder520.common.constants.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * @author 张晨旭
 * @DATE 2018/4/17
 */
@Configurable
@EnableRedisHttpSession
public class HttpSessionConfig {
    @Autowired
    private Parameters parameters;

    /**
     * 生成springsession的验证策略(通过请求头中的token)
     * session存成功了，实际上是自动生成了一个token，以token为key存在了redis里，
     * 然后返回给前端的请求头中会包含这个token，key是x-auth-token
     * session中存的是（Constants.REQUEST_USER_SESSION，用户ue）
     * redis中存的是（用户ue算得的token，用户ue序列化的值）
     * @return
     */
    @Bean
    public HttpSessionStrategy httpSessionStrategy(){
        return  new HeaderHttpSessionStrategy();
    }

    @Bean
    public JedisConnectionFactory connectionFactory(){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        String redisHost = parameters.getRedisNode().split(":")[0];
        int redisPort = Integer.valueOf(parameters.getRedisNode().split(":")[1]);
        connectionFactory.setTimeout(2000);
        connectionFactory.setHostName(redisHost);
        connectionFactory.setPort(redisPort);
        return connectionFactory;
    }
}
