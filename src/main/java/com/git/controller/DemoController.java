package com.git.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.git.domain.AjaxJson;

@RestController
public class DemoController {
	
	@RequestMapping("/index")
	public AjaxJson index(){
		return new AjaxJson(true, "访问成功", null);
	}
}
