package com.guigu.springcloud.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        /**
         * 根据feign支持的日志级别，选择合适的日志级别并配置
         * NONE，BASIC，HEADERS，FULL
         */
        return Logger.Level.FULL;
    }
}
