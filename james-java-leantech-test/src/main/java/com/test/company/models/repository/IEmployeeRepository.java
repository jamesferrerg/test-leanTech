package com.test.company.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.company.models.entity.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer>{
	
	@Query("SELECT e FROM Employee e JOIN e.person p JOIN e.position po "
			+ "WHERE p.name LIKE %?1% OR po.name LIKE %?1%")
	public List<Employee> findByEmployeeNameOrPositionName(String word);
	

}
