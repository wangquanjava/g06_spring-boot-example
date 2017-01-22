package com.git.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.git.domain.AjaxJson;
import com.git.util.RedisUtils;

@RestController
@RequestMapping("redis")
public class RedisController {
	@Value("${env}")
	private String env;
	
	@RequestMapping("/insert")
	public ResponseEntity<AjaxJson> insert(HttpServletRequest request){
		String id = request.getSession().getId();
		RedisUtils.putString("18810629868", "123456", 0);
		return ResponseEntity.ok(new AjaxJson(true, "success", null));
	}
	
}
