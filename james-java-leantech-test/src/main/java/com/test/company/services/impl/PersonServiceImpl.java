package com.test.company.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.company.models.entity.Person;
import com.test.company.models.repository.IPersonRepository;
import com.test.company.services.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService{

	@Autowired
	private IPersonRepository personRepo;
	
	@Override
	@Transactional
	public List<Person> findAll() {
		return (List<Person>) personRepo.findAll();
	}

	@Override
	@Transactional
	public Person findById(Integer id) {
		return personRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Person save(Person person) {
		return personRepo.save(person);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		personRepo.deleteById(id);
	}

}
