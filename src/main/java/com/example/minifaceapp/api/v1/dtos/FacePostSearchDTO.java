package com.example.minifaceapp.api.v1.dtos;

import java.sql.Timestamp;

import com.example.minifaceapp.model.PostType;

import lombok.Data;

@Data
public class FacePostSearchDTO {

	private Long id;
	private String title;
	private String body;
	private FaceUserDTO creator;
	private PostType type;
	private Timestamp creationTime;
	
	private int likes;
}
