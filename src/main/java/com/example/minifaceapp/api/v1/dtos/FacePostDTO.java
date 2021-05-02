package com.example.minifaceapp.api.v1.dtos;

import com.example.minifaceapp.model.PostType;

import lombok.Data;

@Data
public class FacePostDTO {

	private Long id;
	private String title;
	private String body;
	private FaceUserDTO creator;
	private PostType type;
}
