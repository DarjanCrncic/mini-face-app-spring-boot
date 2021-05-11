package com.example.minifaceapp.api.v1.dtos;

import lombok.Data;

@Data
public class FaceGroupSearchDTO {

	private Long id;
	private String name;
	private String description;
	private FaceUserDTO owner;
}
