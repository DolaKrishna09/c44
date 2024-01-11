package com.app.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.app.dao.StudentsDetailsRepository;


@Service
public class StudentsDetailsService {

    @Autowired
    private StudentsDetailsRepository studentsDetailsRepository;
    
    public List<Map<String, Object>> getStudentCourses(int studentId) {
        List<Object[]> result = studentsDetailsRepository.studentCourse(studentId);
        List<Map<String, Object>> studentCourses = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> courseMap = new HashMap<>();
            courseMap.put("studentName", row[0]);
            courseMap.put("courseName", row[1]);
            courseMap.put("courseId", row[2]);  // This is where the exception is thrown

            studentCourses.add(courseMap);
        }

        return studentCourses;
    }

}
