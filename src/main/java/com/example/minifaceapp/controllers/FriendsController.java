package com.example.minifaceapp.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.api.v1.dtos.FaceFriendReqDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.model.FaceFriendReq;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.services.FaceFriendReqService;
import com.example.minifaceapp.services.FaceUserService;
import com.example.minifaceapp.services.StatusService;

@RestController
@RequestMapping("/friends")
public class FriendsController {
	
	private final FaceUserService faceUserService;
	private final FaceFriendReqService faceFriendReqService;
	private final StatusService statusService;
	
	public FriendsController(FaceUserService faceUserService, FaceFriendReqService faceFriendReqService, StatusService statusService) {
		this.faceUserService = faceUserService;
		this.faceFriendReqService = faceFriendReqService;
		this.statusService = statusService;
	}

	@GetMapping("/find")
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> findFriends(Principal principal){
		FaceUser faceUser = faceUserService.findByUsername(principal.getName());
		return faceUserService.findFriends(faceUser);
	}
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> searchFriends(@RequestBody SearchDTO searchDTO, Principal principal){
		System.out.println(searchDTO.toString());
		FaceUser faceUser = faceUserService.findByUsername(principal.getName());
		return faceUserService.searchFaceUsers(searchDTO, faceUser);
	}
	
	@PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceFriendReq sendFriendReq(@RequestBody FaceFriendReqDTO faceFriendReqDTO, Principal principal) {
		FaceUser faceUser = faceUserService.findByUsername(principal.getName());
		FaceFriendReq faceFriendReq = new FaceFriendReq();
		faceFriendReq.setFaceFriendId(faceFriendReqDTO.getFaceFriendId());
		faceFriendReq.setFaceUserId(faceUser.getId());
		faceFriendReq.setStatus(statusService.findById(1L));
		return faceFriendReqService.save(faceFriendReq);		
	}
	
	@GetMapping(value = "/requests", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> getPendingReqs(Principal principal){
		FaceUser faceUser = faceUserService.findByUsername(principal.getName());
		List<Long> ids = faceFriendReqService.findAllByFaceFriendId(faceUser.getId());		
		return faceUserService.findByIdIn(ids);
	}
}
