package com.example.minifaceapp.api.v1.dtos;

import com.example.minifaceapp.model.Status;

import lombok.Data;

@Data
public class FaceGroupReqDTO {
	private Long id;
	private Long userId;
	private Long groupId;
	private Status status;
}
