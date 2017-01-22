package com.git.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.git.dao.DemoSqlDao;
import com.git.domain.DemoEntity;
import com.git.service.DemoSqlService;

@Service
@Transactional
public class DemoSqlServiceImpl implements DemoSqlService{
	@Autowired
	private DemoSqlDao demoSqlDao;
	
	@Override
	public DemoEntity get(Integer id) {
		return this.demoSqlDao.findById(id);
	}

	@Override
	public List<DemoEntity> getByStartDate(Date parseDate, Date date) {
		return this.demoSqlDao.findByStartDateBetween(parseDate,date);
	}

	@Override
	public int update(Integer id) {
		return this.demoSqlDao.update(id);
	}

}
