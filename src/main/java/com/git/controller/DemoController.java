package com.git.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.git.domain.AjaxJson;
import com.git.domain.DemoEntity;
import com.git.service.DemoService;

@RestController
public class DemoController {
	@Autowired
	private DemoService demoService;
	
	
	@RequestMapping("/insert")
	public AjaxJson insert(HttpServletRequest request){
		DemoEntity insert = this.demoService.insert(new DemoEntity());
		return new AjaxJson(true, "访问成功", insert);
	}
	
	@RequestMapping("/get")
	public AjaxJson get(HttpServletRequest request,Integer id){
		DemoEntity demoEntity = this.demoService.get(id);
		return new AjaxJson(true, "访问成功", demoEntity);
	}
	
	
	@RequestMapping("/getByStartDate")
	public AjaxJson getByStartDate(HttpServletRequest request) throws ParseException{
		List<DemoEntity> demoEntitys = this.demoService.getByStartDate(DateUtils.parseDate("2016-12-12", "yyyy-MM-dd"),DateUtils.parseDate("2017-01-12", "yyyy-MM-dd"));
		return new AjaxJson(true, "访问成功", demoEntitys);
	}
	
	
	@RequestMapping("/exception")
	public AjaxJson exception(HttpServletRequest request) throws Exception{
		if (true) {
			throw new RuntimeException("这里是异常信息");
		}
		List<DemoEntity> demoEntitys = this.demoService.getByStartDate(DateUtils.parseDate("2016-12-12", "yyyy-MM-dd"),DateUtils.parseDate("2017-01-12", "yyyy-MM-dd"));
		return new AjaxJson(true, "访问成功", demoEntitys);
	}
	
	
	
}
