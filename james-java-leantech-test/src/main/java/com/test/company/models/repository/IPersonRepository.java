package com.test.company.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.company.models.entity.Person;

public interface IPersonRepository extends JpaRepository<Person, Integer>{

}
