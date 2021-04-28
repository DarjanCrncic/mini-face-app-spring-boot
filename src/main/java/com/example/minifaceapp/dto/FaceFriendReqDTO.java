package com.example.minifaceapp.dto;

import lombok.Data;

@Data
public class FaceFriendReqDTO {
	
	private Long faceUserId;
	private Long faceFriendId;
	private int status;

}
