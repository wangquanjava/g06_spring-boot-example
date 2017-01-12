package com.git;

import java.net.InetAddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) throws Exception {
    	
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationStart.class, args);
        
        
        String port = context.getEnvironment().getProperty("server-port");
        String address = InetAddress.getLocalHost().getHostAddress();
        System.out.println("------访问地址为:"+address+":"+port+"------");
    }
}