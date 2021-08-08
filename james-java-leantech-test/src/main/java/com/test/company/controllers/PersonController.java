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

import com.test.company.models.entity.Person;
import com.test.company.services.IPersonService;

@RestController
@RequestMapping("/api")
public class PersonController {
	
	@Autowired
	private IPersonService personService;
	
	@GetMapping("/people")
	public ResponseEntity<?> listPersons(){
		return ResponseEntity.ok().body(personService.findAll());
	}
	
	@PostMapping("/people")
	public ResponseEntity<?> create(@Valid @RequestBody Person person, BindingResult result){
		Person newPerson = null;
		
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
			newPerson = personService.save(person);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to create a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The person has been created");
		response.put("person", newPerson);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/people/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Person person, BindingResult result, @PathVariable Integer id){
		Person currentPerson= personService.findById(id);
		Person updatePerson = null;
		
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (currentPerson == null) {
			response.put("message", "Error: it cannot update the person with id ".concat(id.toString().concat(", it doesn't exist in data base")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			currentPerson.setName(person.getName());
			currentPerson.setLastName(person.getLastName());
			currentPerson.setAddress(person.getAddress());
			currentPerson.setCellphone(person.getCellphone());
			currentPerson.setCityName(person.getCityName());
			
			updatePerson = personService.save(currentPerson);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to update a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The person has been updated");
		response.put("person", updatePerson);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/people/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			personService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "It has been happened an error to delete a register in the data base");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The person has been deleted");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
