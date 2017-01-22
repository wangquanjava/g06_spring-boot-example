package com.git.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.git.domain.AjaxJson;
import com.git.domain.DemoEntity;
import com.git.service.DemoSqlService;

@RestController
@RequestMapping("/sql")
public class DemoSqlController {
	@Autowired
	private DemoSqlService demoSqlService;
	
	
	@RequestMapping("/get")
	public AjaxJson get(HttpServletRequest request,Integer id){
		DemoEntity demoEntity = this.demoSqlService.get(id);
		return new AjaxJson(true, "访问成功", demoEntity);
	}
	
	@RequestMapping("/update/{id}")
	public AjaxJson update(HttpServletRequest request,@PathVariable Integer id){
		int update = this.demoSqlService.update(id);
		return new AjaxJson(true, "访问成功", update);
	}
	
	
	@RequestMapping("/getByStartDate")
	public AjaxJson getByStartDate(HttpServletRequest request) throws ParseException{
		List<DemoEntity> demoEntitys = this.demoSqlService.getByStartDate(DateUtils.parseDate("2016-12-12", "yyyy-MM-dd"),DateUtils.parseDate("2017-01-12", "yyyy-MM-dd"));
		return new AjaxJson(true, "访问成功", demoEntitys);
	}
}
