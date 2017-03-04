package com.git.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 带数据的返回类
 * @author wangquan
 *
 * @param <T>
 */
@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=false)
public class BaseDataResponse<T> extends BaseResponse{
	private static final long serialVersionUID = 1L;
	private T t;
	
    //1
    public BaseDataResponse(String code,T t){
    	super(code);
    	this.t = t;
    }
    //2
    public BaseDataResponse(String code,String message,T t){
    	super(code,message);
    	this.t = t;
    }
}
