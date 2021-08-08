package com.test.company.services;

import java.util.List;

import com.test.company.models.entity.Position;

public interface IPositionService {
	
	public List<Position> findAll();
	
	public Position findById(Integer id);
	
	public Position save(Position position);
	
	public void delete(Integer id);

}
