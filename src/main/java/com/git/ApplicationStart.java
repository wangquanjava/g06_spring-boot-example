package com.git;

import java.net.InetAddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableAsync
@SpringBootApplication
@ServletComponentScan
@EnableRedisHttpSession
@PropertySources({
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:application-datasource.properties"),
		@PropertySource("classpath:application-log.properties"),
		@PropertySource("classpath:application-redis.properties"),
		@PropertySource("classpath:application-mvc.properties")
	})
public class ApplicationStart {
    public static void main(String[] args) throws Exception {
    	
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationStart.class, args);
        
        
        String port = context.getEnvironment().getProperty("server-port");
        String address = InetAddress.getLocalHost().getHostAddress();
        System.out.println("------访问地址为:"+address+":"+port+"------");
    }
}