package com.test.company.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.company.models.entity.Position;
import com.test.company.services.IPositionService;

@RestController
@RequestMapping("/api")
public class PositionController {
	
	@Autowired
	private IPositionService positionService;
	
	@GetMapping("/positions")
	public ResponseEntity<?> listPosition(){
		return ResponseEntity.ok().body(positionService.findAll());
	}
	
	@PostMapping("/positions")
	public ResponseEntity<?> create(@Valid @RequestBody Position position, BindingResult result){
		Position newPosition = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			newPosition = positionService.save(position);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to create a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The position has been created");
		response.put("person", newPosition);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/positions/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Position position, BindingResult result, @PathVariable Integer id){
		Position currentPosition = positionService.findById(id);
		Position updatePosition = null;
		
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (currentPosition == null) {
			response.put("message", "Error: it cannot update the position with id ".concat(id.toString().concat(", it doesn't exist in data base")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			currentPosition.setName(position.getName());
			
			updatePosition = positionService.save(currentPosition);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to update a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The position has been updated");
		response.put("person", updatePosition);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/positions/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			positionService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to delete a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The position has been deleted");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
