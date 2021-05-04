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

import com.example.minifaceapp.api.v1.dtos.PostCommentDTO;
import com.example.minifaceapp.model.CommentLike;
import com.example.minifaceapp.model.PostLike;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FacePostService;
import com.example.minifaceapp.services.PostCommentService;
import com.example.minifaceapp.services.PostLikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
	
	PostLikeService postLikeService;
	FacePostService facePostService;
	PostCommentService postCommentService;
	
	public LikeController(PostLikeService postLikeService, FacePostService facePostService, PostCommentService postCommentService) {
		this.postLikeService = postLikeService;
		this.facePostService = facePostService;
		this.postCommentService = postCommentService;
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
	
	@PostMapping(value = "comment/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public CommentLike newCommentLike(@RequestBody CommentLike commentLike, @AuthenticationPrincipal CustomUserDetails userDetails) {
		commentLike.setLikerId(userDetails.getId());
		PostCommentDTO postComment = postCommentService.findById(commentLike.getCommentId());
		
		// duplicate check
		for(CommentLike like : postComment.getLikes()) {
			if(like.getCommentId().equals(commentLike.getCommentId()) && like.getLikerId().equals(commentLike.getLikerId()))
				return commentLike;
		}		
		postComment.getLikes().add(commentLike);
		postCommentService.save(postComment);
		return commentLike;
	}
	
	@GetMapping(value = "/comment/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public int getCommentLikes(@PathVariable Long id) {
		PostCommentDTO postComment = postCommentService.findById(id);
		return postComment.getLikes().size();
	}

}
