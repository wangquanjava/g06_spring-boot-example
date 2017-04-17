package com.git;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
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
	private static final Logger logger = Logger.getLogger(ApplicationStart.class);
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
        
        
        showAppInfo(context.getEnvironment());
    }
    public static void showAppInfo(final Environment env) throws UnknownHostException {
        String isSslEnabled = env.getProperty("server.ssl.enabled");
        String protocol = "http";
        if (isSslEnabled != null && "true".equals(isSslEnabled)) {
            protocol = "https";
        }
        logger.info("Access URLs:\n----------------------------------------------------------\n\t" +
                    "Local: \t\t" + protocol + "://127.0.0.1:"+env.getProperty("server.port")+"\n\t" +
                    "External: \t" + protocol + "://"+InetAddress.getLocalHost().getHostAddress()+":"+env.getProperty("server.port")+"\n----------------------------------------------------------"
                );
    }
}