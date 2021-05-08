package com.example.minifaceapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.api.v1.dtos.FaceGroupDTO;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FaceGroupService;
import com.example.minifaceapp.services.FaceUserService;

@RestController
@RequestMapping("/groups")
public class FaceGroupController {
	
	private FaceGroupService faceGroupService;
	private FaceUserService faceUserService;
	

	public FaceGroupController(FaceGroupService faceGroupService, FaceUserService faceUserService) {
		this.faceGroupService = faceGroupService;
		this.faceUserService = faceUserService;
	}
	
	@PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceGroupDTO createNewGroup(@RequestBody FaceGroupDTO faceGroupDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		FaceUser owner = faceUserService.findByUsername(userDetails.getUsername());		
		return faceGroupService.saveNew(faceGroupDTO, owner);
	}
	
	@GetMapping(value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceGroupDTO> getUsersGroups(@AuthenticationPrincipal CustomUserDetails userDetails){
		return faceGroupService.getGroupsFromUser(faceUserService.findByUsername(userDetails.getUsername()));
	}
}
