package com.example.quickstart.config;

import com.example.quickstart.domain.TestProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 配置类config,可以统合其他多个config
 */
@Configuration
@EnableConfigurationProperties({TestProperties.class})    //启用ConfigurationProperties,value可以是数组
public class MyConfiguration {
}
