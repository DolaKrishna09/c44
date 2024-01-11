package com.app.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.dao.CourseRepository;
import com.app.entity.Courses;

@Service
public class CourseService {

	@Autowired
	CourseRepository repository;

	public List<Courses> getAllCoursesWithId() {
		return repository.findAll();
	}

	public List<Object[]> getAllCoursesWithoutId() {
		return repository.allCoursesWithoutId();
	}

	public List<Object[]> getCourseByCourseName(String courseName) {
		return repository.findByCOURSE_NAME(courseName);
	}
	
	
	public Optional<Courses> getCourseByCourseId(int courseId) {
		return repository.findById(courseId);
	}
	
	public List<Object[]> fetchJavaDetails() {
		return repository.fetchJavaDetails();
	}


	public ResponseEntity<String> addCourse(Courses course) {
		repository.save(course);
		return new ResponseEntity<>("Course added successfully", HttpStatus.CREATED);
	}
	

    public ResponseEntity<String> addCourseWithId(int courseId, Courses course) {
        course.setCourseId(courseId);
        repository.save(course);
        return new ResponseEntity<>("Course added successfully with ID: " + courseId, HttpStatus.CREATED);
    }


	// Service method
	public ResponseEntity<String> updateCourse(int courseId, Courses updatedCourse) {
	    Optional<Courses> existingCourse = repository.findById(courseId);

	    if (existingCourse.isPresent()) {
	        Courses courseToUpdate = existingCourse.get();
	        courseToUpdate.setCourseName(updatedCourse.getCourseName());
	        courseToUpdate.setCourseDuration(updatedCourse.getCourseDuration());
	        courseToUpdate.setStartDate(updatedCourse.getStartDate());
	        courseToUpdate.setEndDate(updatedCourse.getEndDate());
	        courseToUpdate.setAvailability(updatedCourse.getAvailability());
	        courseToUpdate.setDetails(updatedCourse.getDetails());

	        try {
	            repository.save(courseToUpdate);
	            return new ResponseEntity<>("Course updated successfully", HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            // Handle the exception, e.g., log it
	            return new ResponseEntity<>("Failed to update course", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } else {
	        return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
	    }
	}


	public ResponseEntity<String> deleteCourse(int courseId) {

		Optional<Courses> existingCourse = repository.findById(courseId);

		if (existingCourse.isPresent()) {

			repository.deleteById(courseId);
			return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
		} else {

			return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
		}
	}
}
