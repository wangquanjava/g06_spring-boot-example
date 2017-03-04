package com.git.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.git.domain.BaseDataResponse;
import com.git.domain.DemoEntity;
import com.git.domain.ResultCode;
import com.git.service.DemoService;

@RestController
public class DemoController {
	@Autowired
	private DemoService demoService;
	
	
	@RequestMapping("/get")
	public BaseDataResponse<DemoEntity> get(HttpServletRequest request,Integer id){
		DemoEntity demoEntity = this.demoService.get(id);
		System.out.println(0/0);
		return new BaseDataResponse<DemoEntity>(ResultCode.SUCCESSFUL_CODE, demoEntity);
	}
}
