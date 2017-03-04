package com.git.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.git.domain.BaseResponse;


/**
 * @author wangquan
 * 全局异常类
 */
@ControllerAdvice
public class GlobeException {
    private static Logger logger = LoggerFactory.getLogger(GlobeException.class);
	/**
	 * 这里的接收参数，和controller中的接受参数一样，可以根据自己需要进行改变
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Object runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception ex) {
		// 打印错误
        logger.error("do [{}] on [{}] failed. exMsg:{}", request.getMethod(), request.getRequestURL(),ex.getLocalizedMessage());
        ex.printStackTrace();
        
		// 判断不同的请求进行返回
		if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			return this.processAjax();
		} else {
			return this.processNormal(response);
		}
	}

	/**
	 * 针对ajax错误的返回
	 * 
	 * @param response
	 * @return
	 */
	private ResponseEntity<BaseResponse> processAjax() {
		return ResponseEntity.status(500).body(BaseResponse.FAIL());
	}

	/**
	 * 针对普通请求的返回
	 * 
	 * @param response
	 * @return
	 */
	private ModelAndView processNormal(HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("500");
		modelAndView.addObject("msg", "出现异常了");
		return modelAndView;
	}
}
