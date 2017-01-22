package com.git.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.git.domain.DemoEntity;

@Repository
public interface DemoSqlDao extends JpaRepository<DemoEntity, Integer>{
	
	@Query("from DemoEntity demoEntity where demoEntity.id = :id")
	public DemoEntity findById(@Param("id")Integer id);
	
	public List<DemoEntity> findByStartDateBetween(Date startDate,Date endDate);

	@Query("update DemoEntity demo set demo.startDate='2017-01-01' where demo.id=:id")
	@Modifying
	public int update(@Param("id")Integer id);


	
}
