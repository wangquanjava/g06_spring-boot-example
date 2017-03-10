package com.git.auto.requestlog;

import java.util.Map;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class LogEntity {
	private String method;
	private String ip;
	private String requestURL;
	
	private Map<String, String[]> paramMap;
	private String queryString;
	private String body;
	private String result;
	private long costTime;
}
