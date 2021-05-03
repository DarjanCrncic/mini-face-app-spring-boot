package com.example.minifaceapp.api.v1.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostCommentDTO {

	private Long id;
	private String body;
	private FaceUserDTO faceUserDTO;
	private Long postId;
	private LocalDateTime creationTime;
}
