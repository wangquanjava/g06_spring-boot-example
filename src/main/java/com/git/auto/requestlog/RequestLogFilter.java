package com.git.auto.requestlog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.git.util.HttpServletRequestCopier;
import com.git.util.HttpServletResponseCopier;
import com.git.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * http请求日志记录 Created by aqlu on 15/10/13.
 */
public class RequestLogFilter extends GenericFilterBean {
	private static final Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);

	private static final Logger requestLog = LoggerFactory.getLogger("requestLog");

	// 下面设置几个默认值
	private int maxResultLength = 512;
	private int maxBodyLength = 512;

	/**
	 * 初始化参数
	 */
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();

		//从filter注册类初始化参数
		maxResultLength = Integer.parseInt(this.getFilterConfig().getInitParameter("MAX_RESULT_LENGTH"));
		maxBodyLength = Integer.parseInt(this.getFilterConfig().getInitParameter("MAX_BODY_LENGTH"));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			// 设置开始时间，用来计算程序运行时间
			Long startTimeMills = System.currentTimeMillis();

			// 设置编码
			if (response.getCharacterEncoding() == null) {
				response.setCharacterEncoding("UTF-8");
			}

			//创建request和response的复制类,并进行请求
			HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
			HttpServletRequestCopier requestCopier = new HttpServletRequestCopier((HttpServletRequest) request);
			
			LogEntity logEntity = new LogEntity();
			try {
				//尝试拼装请求相关参数
				logEntity.setIp(Utils.getRealRemoteIp(httpRequest));
				logEntity.setMethod(httpRequest.getMethod());
				logEntity.setRequestURL(httpRequest.getRequestURL().toString());
				logEntity.setParamMap(httpRequest.getParameterMap());
				logEntity.setQueryString(httpRequest.getQueryString());
				logEntity.setBody(truncString(getRequestBody(request, requestCopier), maxBodyLength));
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			
			//通过请求
			chain.doFilter(requestCopier, responseCopier);
			
			try {
				//尝试拼装响应参数
				logEntity.setResult(truncString(getOutputParamToStr(response, responseCopier), maxResultLength));
				logEntity.setCostTime(System.currentTimeMillis() - startTimeMills);
			} catch(Exception e){
				logger.error(e.getMessage());
			} finally {
				//打印日志
				requestLog.info(new GsonBuilder().setPrettyPrinting().create().toJson(logEntity));
			}
		}
	}

	/**
	 * 得到response中的结果
	 * @param response
	 * @param responseCopier
	 * @return
	 */
	private String getOutputParamToStr(ServletResponse response, HttpServletResponseCopier responseCopier) {
		try {
			responseCopier.flushBuffer();
			byte[] copy = responseCopier.getCopy();
			return new String(copy, response.getCharacterEncoding());
		} catch (Exception e) {
			logger.warn("http接口日志返回值封装失败", e);
		}
		return null;
	}

	private static final String SUB_SUFFIX = "...(source length is %s)";

	/**
	 * 截取字符串长度，超过用上面的SUB_SUFFIX替换
	 * @param str
	 * @param maxLength
	 * @return
	 */
	private String truncString(String str, int maxLength) {
		if (str == null) {
			return null;
		}

		if (str.length() > maxLength) {
			String subSuffix = String.format(SUB_SUFFIX, str.length());
			return str.substring(0, maxLength - subSuffix.length()) + subSuffix;
		}

		return str;
	}

	/**
	 * 获得请求body体(post中才有值)
	 * @param request
	 * @param requestCopier
	 * @return
	 */
	private String getRequestBody(ServletRequest request, HttpServletRequestCopier requestCopier) {
		try {
			if (request.getContentType() != null && request.getContentType().contains("multipart/form-data")) {
				// 如果是文件上传的，就直接返回长度
				return "multipart/form-data, contentLength is: " + request.getContentLength();
			}

			byte[] bytes = requestCopier.getCopy();
			String charset = request.getCharacterEncoding();
			if (charset != null) {
				return new String(bytes, charset);
			} else {
				return new String(bytes);
			}
		} catch (Exception ex) {
			logger.warn("http接口日志参数体封装失败", ex);
		}

		return null;
	}

}