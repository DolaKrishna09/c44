package com.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Service.ModuleService;

import com.app.entity.Modules;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/module")
public class ModuleController {

	@Autowired
	ModuleService moduleservice;
    
	
	@GetMapping("/getAllModules")
	public List<Modules> getAllModules() {
		return moduleservice.getAllModules();

	}
    
	@GetMapping("/getModuleByModuleId/{moduleId}")
	public Optional<Modules> getModuleByModuleId(@PathVariable Integer moduleId) {
	    return moduleservice.getModuleByModuleId(moduleId);
	}

	@PostMapping("/addModule")
	public ResponseEntity<String> addModule(@RequestBody Modules module) {
		return moduleservice.addModule(module);
	}

	// Update a module by ID
	@PutMapping("/updateModule/{moduleId}")
	public ResponseEntity<String> updateModule(@PathVariable int moduleId, @RequestBody Modules updatedModule) {
		return moduleservice.updateModule(moduleId, updatedModule);
	}

	// Delete a module by ID
	@DeleteMapping("/deleteModule/{moduleId}")
	public ResponseEntity<String> deleteModule(@PathVariable int moduleId) {
		return moduleservice.deleteModule(moduleId);
	}

}