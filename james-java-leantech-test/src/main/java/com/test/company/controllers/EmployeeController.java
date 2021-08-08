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

import com.test.company.models.entity.Employee;
import com.test.company.services.IEmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	private IEmployeeService employeeService;
	
	@GetMapping("/employees")
	public ResponseEntity<?> listEmployees(){
		return ResponseEntity.ok().body(employeeService.findAll());
	}
	
	@GetMapping("/employees/{word}")
	public ResponseEntity<?> filter(@PathVariable String word){
		return ResponseEntity.ok(employeeService.findByEmployeeNameOrPositionName(word));
	}
	
	@PostMapping("/employees")
	public ResponseEntity<?> create(@Valid @RequestBody Employee employee, BindingResult result){
		Employee newEmployee = null;
		
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
			newEmployee = employeeService.save(employee);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to create a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The employee has been created");
		response.put("employee", newEmployee);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Employee employee, BindingResult result, @PathVariable Integer id){
		Employee currentEmployee = employeeService.findById(id);
		Employee updateEmployee = null;
		
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (currentEmployee == null) {
			response.put("message", "Error: it cannot update the employee with id ".concat(id.toString().concat(", it doesn't exist in data base")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			currentEmployee.setSalary(employee.getSalary());
			currentEmployee.setPerson(employee.getPerson());
			currentEmployee.setPosition(employee.getPosition());
			
			updateEmployee = employeeService.save(currentEmployee);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to update a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The employee has been updated");
		response.put("employee", updateEmployee);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			employeeService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to delete a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The employee has been deleted");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
