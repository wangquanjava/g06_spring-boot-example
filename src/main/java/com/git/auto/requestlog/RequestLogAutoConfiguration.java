package com.git.auto.requestlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用来注册filter
 * 0.@EnableConfigurationProperties用来导入配置类
 * 1.@ConditionalOnProperty来判断是否开启过滤器
 * 2.
 */
@Configuration
@EnableConfigurationProperties(RequestLogProperties.class)
public class RequestLogAutoConfiguration {

    @Autowired
    private RequestLogProperties requestLogProperties;

    @Bean
    @ConditionalOnProperty(prefix = "request.log", name = "enable", havingValue = "true")
    public FilterRegistrationBean requestLogFilter() {
    	//创建注册类对象
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        
        //传入filter对象
        registrationBean.setFilter(new RequestLogFilter());
        
        //传入初始化参数
        registrationBean.addInitParameter("MAX_RESULT_LENGTH", String.valueOf(requestLogProperties.getMaxResultLength()));
        registrationBean.addInitParameter("MAX_BODY_LENGTH", String.valueOf(requestLogProperties.getMaxBodyLength()));
        
        //传入匹配路径
        registrationBean.addUrlPatterns(requestLogProperties.getUrlPatterns());
        
        //设置filter的优先级
        registrationBean.setOrder(requestLogProperties.getOrder());

        return registrationBean;
    }
}
