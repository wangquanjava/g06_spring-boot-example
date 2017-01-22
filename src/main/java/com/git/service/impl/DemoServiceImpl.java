package com.git.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.git.dao.DemoDao;
import com.git.domain.DemoEntity;
import com.git.service.DemoService;

@Service
@Transactional(readOnly=true,timeout=10)
public class DemoServiceImpl implements DemoService{
	@Autowired
	private DemoDao demoDao;
	
	@Override
	@Transactional
	public DemoEntity insert(DemoEntity demoEntity) {
		DemoEntity save = this.demoDao.save(demoEntity);
		return save;
	}

	@Override
	public DemoEntity get(Integer id) {
		return this.demoDao.findById(id);
	}

	@Override
	public List<DemoEntity> getByStartDate(Date parseDate, Date date) {
		return this.demoDao.findByStartDateBetween(parseDate,date);
	}

}
