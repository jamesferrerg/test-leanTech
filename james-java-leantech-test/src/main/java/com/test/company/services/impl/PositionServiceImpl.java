package com.test.company.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.company.models.entity.Position;
import com.test.company.models.repository.IPositionRepository;
import com.test.company.services.IPositionService;

@Service
public class PositionServiceImpl implements IPositionService{

	@Autowired
	private IPositionRepository positionRepo;
	
	@Override
	@Transactional
	public List<Position> findAll() {
		return (List<Position>) positionRepo.findAll();
	}

	@Override
	@Transactional
	public Position findById(Integer id) {
		return positionRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Position save(Position position) {
		return positionRepo.save(position);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		positionRepo.deleteById(id);
	}
	
}
