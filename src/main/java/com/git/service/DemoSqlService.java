package com.git.service;

import java.util.Date;
import java.util.List;

import com.git.domain.DemoEntity;

public interface DemoSqlService {

	DemoEntity get(Integer id);

	List<DemoEntity> getByStartDate(Date parseDate, Date date);

	int update(Integer id);

}
