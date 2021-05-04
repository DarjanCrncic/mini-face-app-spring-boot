package com.example.minifaceapp.api.v1.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.minifaceapp.model.CommentLike;

import lombok.Data;

@Data
public class PostCommentDTO {

	private Long id;
	private String body;
	private FaceUserDTO faceUserDTO;
	private Long postId;
	private LocalDateTime creationTime;
	private Set<CommentLike> likes;
}
