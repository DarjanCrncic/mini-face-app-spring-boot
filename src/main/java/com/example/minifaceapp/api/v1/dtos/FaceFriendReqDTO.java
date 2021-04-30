package com.example.minifaceapp.api.v1.dtos;

import com.example.minifaceapp.model.Status;

import lombok.Data;

@Data
public class FaceFriendReqDTO {
	
	private Long id;
	private Long faceUserId;
	private Long faceFriendId;
	private Status status;

}
