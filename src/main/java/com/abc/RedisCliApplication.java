package com.abc;

import com.abc.utils.JedisClusterHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@Configuration
public class RedisCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisCliApplication.class, args);
    }

    @Value("${redis.cluster.nodes:127.0.0.1:6379}")
    private String redisClusterNode = null;

    @Bean
    public ApplicationListener<ContextRefreshedEvent> applicationListener() {
        return new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                JedisClusterHolder.getInstance().register(redisClusterNode, getJedisCluster());
            }
        };
    }

    @Bean
    public JedisCluster getJedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(HostAndPort.parseString(redisClusterNode));
        return new JedisCluster(jedisClusterNodes);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        return validator;
    }

    @Bean
    public MethodValidationPostProcessor getValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(getValidator());
        return processor;
    }

}
