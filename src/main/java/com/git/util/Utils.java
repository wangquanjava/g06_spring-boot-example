package com.git.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 工具库
 * Created by aqlu on 15/11/23.
 */
public final class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * 获取真实的远端IP
     *
     * @param request HttpServletRequest
     * @return ip
     */
    public static String getRealRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        logger.debug("X-Real-IP is {}", ip);

        if (!hasText(ip)) {
            ip = request.getHeader("REMOTE-HOST");
            logger.debug("REMOTE-HOST is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("x-forwarded-for");
            logger.debug("x-forwarded-for is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.debug("Proxy-Client-IP is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.debug("WL-Proxy-Client-IP is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.debug("HTTP_CLIENT_IP is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.debug("HTTP_X_FORWARDED_FOR is {}", ip);
        }

        if (hasText(ip)) {
            StringTokenizer st = new StringTokenizer(ip, ",");

            //多级反向代理时，取第一个IP
            if(st.countTokens() > 1){
                ip = st.nextToken();
            }
        }else{
            // 未获取到反向代理IP，返回RemoteAddr
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 获取request参数map的字符串格式
     * @param request ServletRequest
     * @return 字符串
     */
    public static String getRequestParameterMapAsString(ServletRequest request){
        StringBuilder requestParams = new StringBuilder();
        requestParams.append('{');
        Map<String, String[]> paramMap = request.getParameterMap();
        boolean isFirst = true;
        for(Map.Entry<String, String[]> entry: paramMap.entrySet()){
            String name = entry.getKey();
            String[] values = entry.getValue();

            if(!isFirst){
                requestParams.append(",");
            }

            requestParams.append(String.format("%s = %s", name, Arrays.toString(values)));

            isFirst = false;
        }
        requestParams.append('}');

        return requestParams.toString();
    }

    /**
     * 判断字符串是否有内容
     *
     * @param str 待判断的字符串
     * @return 结果
     */
    public static boolean hasText(String str) {
        return str != null && str.length() > 0;
    }
}
