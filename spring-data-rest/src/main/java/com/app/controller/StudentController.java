package com.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Service.StudentsDetailsService;


@CrossOrigin(value="http://localhost:3000")
@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
    StudentsDetailsService studentDetailsService;


    @GetMapping("/courses/{studentId}")
    public ResponseEntity<List<Map<String, Object>>> getStudentCourses(@PathVariable int studentId) {
        List<Map<String, Object>> studentCourses = studentDetailsService.getStudentCourses(studentId);
        return new ResponseEntity<>(studentCourses, HttpStatus.OK);
    }

}
