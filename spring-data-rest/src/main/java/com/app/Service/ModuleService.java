package com.app.Service;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.app.dao.ModulesRepository;

import com.app.entity.Modules;




@Service
public class ModuleService {

@Autowired
private ModulesRepository modulesRepository;




public List<Modules> getAllModules(){
return modulesRepository.findAll();
}



public Optional<Modules> getModuleByModuleId(Integer moduleId) {
    return modulesRepository.findById(moduleId);
}

public ResponseEntity<String> addModule(Modules module) {
    modulesRepository.save(module);
    return new ResponseEntity<>("Module added successfully", HttpStatus.CREATED);
}




//Service method for updating a module
public ResponseEntity<String> updateModule(int moduleId, Modules updatedModule) {
 Optional<Modules> existingModule = modulesRepository.findById(moduleId);

 if (existingModule.isPresent()) {
     Modules moduleToUpdate = existingModule.get();
     moduleToUpdate.setCourseId(updatedModule.getCourseId());
     moduleToUpdate.setModuleName(updatedModule.getModuleName());
     moduleToUpdate.setPrerequisites(updatedModule.getPrerequisites());
     moduleToUpdate.setOutcome(updatedModule.getOutcome());
     moduleToUpdate.setModuleNotes(updatedModule.getModuleNotes());

     try {
    	 modulesRepository.save(moduleToUpdate);
         return new ResponseEntity<>("Module updated successfully", HttpStatus.NO_CONTENT);
     } catch (Exception e) {
         // Handle the exception, e.g., log it
         return new ResponseEntity<>("Failed to update module", HttpStatus.INTERNAL_SERVER_ERROR);
     }
 } else {
     return new ResponseEntity<>("Module not found", HttpStatus.NOT_FOUND);
 }
}


//Service method for deleting a module
public ResponseEntity<String> deleteModule(int moduleId) {
 Optional<Modules> existingModule = modulesRepository.findById(moduleId);

 if (existingModule.isPresent()) {
     try {
    	 modulesRepository.deleteById(moduleId);
         return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
     } catch (Exception e) {
         // Handle the exception, e.g., log it
         return new ResponseEntity<>("Failed to delete module", HttpStatus.INTERNAL_SERVER_ERROR);
     }
 } else {
     return new ResponseEntity<>("Module not found", HttpStatus.NOT_FOUND);
 }
}


















}