package com.git;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableAsync
@SpringBootApplication
@ServletComponentScan
@EnableRedisHttpSession
@PropertySources({
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:application-datasource.properties"),
		@PropertySource("classpath:application-log.properties"),
		@PropertySource("classpath:application-mvc.properties")
	})
public class ApplicationStart extends WebMvcConfigurerAdapter{
	@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false)
                .ignoreAcceptHeader(false)
                .favorParameter(true)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }
	
    public static void main(String[] args) throws Exception {
    	
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationStart.class, args);
        
        
        String address = InetAddress.getLocalHost().getHostAddress();
        String port = context.getEnvironment().getProperty("server-port");
        port = StringUtils.isBlank(port)==true?"8080":port;
        System.out.println("------访问地址为:"+address+":"+port+"------");
    }
}