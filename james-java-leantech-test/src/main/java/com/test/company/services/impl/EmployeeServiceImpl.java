package com.test.company.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.company.models.entity.Employee;
import com.test.company.models.repository.IEmployeeRepository;
import com.test.company.services.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService{
	
	@Autowired
	private IEmployeeRepository employeeRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		return (List<Employee>) employeeRepo.findAll();
	}

	@Override
	@Transactional
	public List<Employee> findByEmployeeNameOrPositionName(String word) {
		return employeeRepo.findByEmployeeNameOrPositionName(word);
	}

	@Override
	@Transactional
	public Employee findById(Integer id) {
		return employeeRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Employee save(Employee employee) {
		return employeeRepo.save(employee);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		employeeRepo.deleteById(id);
	}

}
