package com.test.company.services;

import java.util.List;

import com.test.company.models.entity.Employee;

public interface IEmployeeService {
	
	public List<Employee> findAll();
	
	public List<Employee> findByEmployeeNameOrPositionName(String word);
	
	public Employee findById(Integer id);
	
	public Employee save(Employee employee);
	
	public void delete(Integer id);

}
