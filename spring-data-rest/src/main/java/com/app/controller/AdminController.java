package com.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.Service.CourseService;
import com.app.Service.FileService;
import com.app.Service.ImageService;
import com.app.Service.ModuleService;
import com.app.Service.StudentsDetailsService;
import com.app.Service.VideoService;
import com.app.dao.VideoRepository;
import com.app.dtos.FileModel;
import com.app.entity.Courses;
import com.app.entity.ImagesEntity;
import com.app.entity.ModuleEntity;
import com.app.entity.StudentsDetails;
import com.app.entity.Video;

import jakarta.servlet.http.HttpServletResponse;


@CrossOrigin(value="http://localhost:3000")
@RestController
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private StudentsDetailsService studentsDetailsService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private VideoService videoservice ;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.video}")
	private String path;
	
	@Autowired
	private VideoRepository videorepository;
	

	
	// FOR ADDING COURSE
	@PostMapping("/addCourse")
	public ResponseEntity<String> addCourse(@RequestBody Courses course) {
		return courseService.addCourse(course);
	}
	
	 @PostMapping("/addCourse/{courseId}")
	    public ResponseEntity<String> addCourse(@PathVariable int courseId, @RequestBody Courses course) {
	        return courseService.addCourseWithId(courseId, course);
	    }

	// FETCHING COURSES WITH ID
	@GetMapping("/allCoursesWithId")
	public List<Courses> allCoursesWithId() {
		return courseService.getAllCoursesWithId();
	}

	// FETCHING COURSES WITHOUT ID
	@GetMapping("/allCoursesWithoutId")
	public List<Object[]> allCoursesWithoutId() {
		return courseService.getAllCoursesWithoutId();
	}
	
	@PutMapping("/updateCourse/{courseId}")
	public ResponseEntity<String> updateCourse(@PathVariable int courseId, @RequestBody Courses updatedCourse) {
	    return courseService.updateCourse(courseId, updatedCourse);
	}
	
	

	// DELETE COURSE ACCORDING TO ID
	@DeleteMapping("/deleteCourse/{courseId}")
	public ResponseEntity<String> deleteCourse(@PathVariable int courseId) {
		return courseService.deleteCourse(courseId);
	}

	
	
	// -----------------------------------------------------------------//

	// FOR ADDING STUDENT_DETAILS
	@PostMapping("/addStudentDetails")
	public ResponseEntity<String> addStudentDetails(@RequestBody StudentsDetails studentDetails) {
		return studentsDetailsService.addStudentsDetails(studentDetails);
	}

	// COURSE BOUGHT STUDENTS
	@GetMapping("/allStudentsDetails")
	public List<StudentsDetails> getAllStudentsDetails() {
		return studentsDetailsService.allStudentsDetails();
	}

	// UPDATE STUDENT DETAILS
	@PutMapping("/updateStudentDetails")
	public ResponseEntity<String> updateStudentDetails(@RequestBody StudentsDetails studentDetails) {
		return studentsDetailsService.updateStudentsDetails(studentDetails);
	}

	// DELETE STUDENT DETAILS ACCORDING TO ID
	@DeleteMapping("/deleteStudentDetails/{studentId}")
	public ResponseEntity<String> deleteStudentDetails(@PathVariable int studentId) {
		return studentsDetailsService.deleteStudentDetails(studentId);
	}

	// ---------------------------------------------------------------------------//

	// ADDING IMAGE
	@PostMapping("/addImage")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
		try {
			String response = imageService.uploadImage(file);
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Error uploading the image: " + e.getMessage());
		}
	}

	// DELETING IMAGE
	@DeleteMapping("/deleteImage/{imageId}")
	public ResponseEntity<String> deleteImage(@PathVariable int imageId) {
		String response = imageService.deleteImage(imageId);
		if (response.contains("successfully")) {
			return ResponseEntity.ok(response);
		} else {
			return ((BodyBuilder) ResponseEntity.notFound()).body(response);
		}
	}

	@GetMapping("/getImagewithId")
	public List<ImagesEntity> getImageWithId() {
		return imageService.getImageWithId();
	}

	@GetMapping("/getImagewithOutId")
	public List<byte[]> getImageWithOutId() {
		return imageService.getImageWithOutId();
	}

	// -----------------------------------------------------------------------------//

	// MODULES

    
    //------------------------------------------------------------------------------------------------
    

    
    @PostMapping("/saveVideo")
    public ResponseEntity<Video> saveVideo(@RequestBody Video video) {
        Video savedVideo = videoservice.createPost(video);
        return new ResponseEntity<>(savedVideo, HttpStatus.OK);
    }
    
    
    @GetMapping("/getVideoById/{id}")
    public Video getVideobyid(@PathVariable Integer id) {
    	return videoservice.getVideoById(id);
    }
    
    //API for uploading video
    @PostMapping("/post/{id}")
    public Video uploadingVideo(@RequestParam("video")MultipartFile video, @PathVariable Integer id) throws IOException   {
		Video v=videoservice.getVideoById(id);
		FileModel fileModel=fileservice.uploadVideo(path, video);
		v.setVideoName(fileModel.getVideoFileName());
		Video finallyUpload = videoservice.updatePost(v,id);
		return finallyUpload;
    }
    
  //4.To play video .
  	@GetMapping(value = "/play/{id}", produces = MediaType.ALL_VALUE)
  	public void playVideo(@PathVariable Integer id, HttpServletResponse response) throws IOException {
  		Optional<Video> video = videorepository.findById(id);
  		InputStream resource = this.fileservice.getVideoFile(path, video.get().getVideoName(), id);
  		response.setContentType(MediaType.ALL_VALUE);
  		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
  	}
    
   
    	
    
}
