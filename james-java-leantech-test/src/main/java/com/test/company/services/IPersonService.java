package com.test.company.services;

import java.util.List;

import com.test.company.models.entity.Person;

public interface IPersonService {
	
	public List<Person> findAll();
	
	public Person findById(Integer id);
	
	public Person save(Person person);
	
	public void delete(Integer id);

}
