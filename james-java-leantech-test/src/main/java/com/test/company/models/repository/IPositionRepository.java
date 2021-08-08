package com.test.company.models.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.test.company.models.entity.Position;

public interface IPositionRepository extends JpaRepository<Position, Integer>{
	
}
