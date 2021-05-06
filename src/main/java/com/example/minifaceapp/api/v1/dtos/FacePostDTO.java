package com.example.minifaceapp.api.v1.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.example.minifaceapp.model.PostComment;
import com.example.minifaceapp.model.PostLike;
import com.example.minifaceapp.model.PostType;

import lombok.Data;

@Data
public class FacePostDTO {

	private Long id;
	private String title;
	private String body;
	private FaceUserDTO creator;
	private PostType type;
	private LocalDateTime creationTime;
	
	private Set<PostLike> likes;
	private List<PostComment> comments;
}
