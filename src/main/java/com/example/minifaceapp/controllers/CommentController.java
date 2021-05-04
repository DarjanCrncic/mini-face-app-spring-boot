package com.example.minifaceapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.api.v1.dtos.PostCommentDTO;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FaceUserService;
import com.example.minifaceapp.services.PostCommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	PostCommentService postCommentService;
	FaceUserService faceUserService;

	public CommentController(PostCommentService postCommentService, FaceUserService faceUserService) {
		this.postCommentService = postCommentService;
		this.faceUserService = faceUserService;
	}

	@PostMapping(value = "/post/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public PostCommentDTO createNewComment(@RequestBody PostCommentDTO postCommentDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		return postCommentService.save(postCommentDTO, faceUserService.findByUsername(userDetails.getUsername()));
	}
	
	@GetMapping(value = "/post/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<PostCommentDTO> getCommentsForPost(@PathVariable Long id){
		return postCommentService.getAllCommentsForPost(id);
	}
	
	@DeleteMapping("/post/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePostComment(@PathVariable Long id) {
		postCommentService.deleteById(id);
	}
}
