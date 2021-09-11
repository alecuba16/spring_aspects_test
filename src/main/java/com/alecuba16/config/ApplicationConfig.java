package com.alecuba16.config;

import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.alecuba16")
@EnableAspectJAutoProxy
public class ApplicationConfig {

}
