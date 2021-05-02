package com.example.minifaceapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.model.PostLike;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FacePostService;
import com.example.minifaceapp.services.PostLikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
	
	PostLikeService postLikeService;
	FacePostService facePostService;
	
	public LikeController(PostLikeService postLikeService, FacePostService facePostService) {
		this.postLikeService = postLikeService;
		this.facePostService = facePostService;
	}
	
	@PostMapping(value = "/post/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public PostLike newPostLike(@RequestBody PostLike postLike, @AuthenticationPrincipal CustomUserDetails userDetails){
		postLike.setLikerId(userDetails.getId());		
		return postLikeService.save(postLike);	
	}
	
	@GetMapping(value = "/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public int getPostLikes(@PathVariable Long id) {
		return postLikeService.getPostLikeCount(id);
	}
	

}
