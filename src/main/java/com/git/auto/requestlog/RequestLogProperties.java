package com.git.auto.requestlog;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

/**
 * Request日志配置
 * Created by aqlu on 15/11/20.
 */
@ConfigurationProperties(prefix = "request.log")
@ConditionalOnMissingBean
@Data
public class RequestLogProperties {
    private static final String[] DEFAULT_URL_MAPPINGS = { "/*" };

    /**
     * 是否启用Request日志
     */
    private boolean enable = false;

    /**
     * 最大返回结果长度，超过此长度记录时会被截取
     */
    private int maxResultLength = 512;

    /**
     * 最大请求参数体长度，超过此长度记录时会被截取
     */
    private int maxBodyLength = 512;

    /**
     * 过滤器路径, 多个可以使用","分隔
     */
    private String[] urlPatterns = DEFAULT_URL_MAPPINGS;

    /**
     * 过滤器加载顺序，数字越小优先级越高，可以是负数；
     */
    private int order = Ordered.HIGHEST_PRECEDENCE;
}
