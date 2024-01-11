package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.app.entity.Courses;
import com.app.entity.StudentsDetails;

@RepositoryRestResource
public interface StudentsDetailsRepository extends JpaRepository<StudentsDetails, Integer> {
	
	@Query("SELECT s.studentName, c.courseName, c.courseId FROM StudentsDetails s INNER JOIN Courses c ON c.courseId = s.courses WHERE s.studentId = :studentId")
	public List<Object[]> studentCourse(@Param("studentId") int studentId);
}
