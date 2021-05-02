package com.example.minifaceapp.api.v1.dtos;

import java.sql.Date;

import lombok.Data;

@Data
public class FacePostSearchDTO {

	private Long id;
	private String title;
	private String body;
	private Long creatorId;
	private Long type;
	private String creatorName;
	private Date creationTime;
	
	private int likes;
}
