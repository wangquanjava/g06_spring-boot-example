package com.git.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.git.domain.DemoEntity;

@Repository
public interface DemoDao extends JpaRepository<DemoEntity, Integer>{
	
	public DemoEntity findById(Integer id);
	
	public List<DemoEntity> findByStartDateBetween(Date startDate,Date endDate);
	
}
