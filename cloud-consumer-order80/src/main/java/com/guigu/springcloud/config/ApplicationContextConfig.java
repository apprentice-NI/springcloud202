package com.guigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


//这里类似与miaosha项目中配置的redisTemplate
@Configuration
public class ApplicationContextConfig {
    //相当于在applicationContext.xml文件中配置<bean id="" class="">
    @Bean
    /**
     * @loadbalance注解的作用是开启restTemplate负载均衡的功能
     */
//    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
