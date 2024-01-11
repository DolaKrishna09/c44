package com.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.Exception.ResourseNotFoundException;
import com.app.Service.FileService;
import com.app.Service.VideoService;
import com.app.dao.VideoRepository;
import com.app.dtos.FileModel;
import com.app.entity.Video;

import jakarta.servlet.http.HttpServletResponse;


@CrossOrigin(value="http://localhost:3000")
@RestController
@RequestMapping("/video")
public class VideoController {
	
	

	@Autowired
	private VideoService videoservice ;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.video}")
	private String path;
	
	@Autowired
	private VideoRepository videorepository;
	
	
	
	

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
    
  	@DeleteMapping("/deleteVideo/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Integer id) {
        try {
            Video deletedVideo = videoservice.deleteVideos(id);
            return new ResponseEntity<>("Deleted video with ID: " + deletedVideo.getVidId(), HttpStatus.OK);
        } catch (ResourseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
