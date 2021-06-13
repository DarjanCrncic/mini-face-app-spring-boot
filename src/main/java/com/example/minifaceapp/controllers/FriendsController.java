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

import com.example.minifaceapp.api.v1.dtos.FaceFriendReqDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FaceFriendReqService;
import com.example.minifaceapp.services.FaceUserService;
import com.example.minifaceapp.services.StatusService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/friends")
@AllArgsConstructor
public class FriendsController {
	
	private final FaceUserService faceUserService;
	private final FaceFriendReqService faceFriendReqService;
	private final StatusService statusService;
	
	@GetMapping("/find")
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> findFriends(@AuthenticationPrincipal CustomUserDetails userDetails){
		FaceUser faceUser = faceUserService.findByUsername(userDetails.getUsername());
		return faceUserService.findFriends(faceUser);
	}
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> searchFriends(@RequestBody SearchDTO searchDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
		return faceUserService.searchFaceUsers(searchDTO, userDetails.getId());
	}
	
	@PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceFriendReqDTO sendFriendReq(@RequestBody FaceFriendReqDTO faceFriendReqDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		faceFriendReqDTO.setFaceUserId(userDetails.getId());
		faceFriendReqDTO.setStatus(statusService.findById(1L));
		return faceFriendReqService.save(faceFriendReqDTO);		
	}
	
	@GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> getPendingReqs(@AuthenticationPrincipal CustomUserDetails userDetails){
		List<Long> ids = faceFriendReqService.findAllByFaceFriendId(userDetails.getId());		
		return faceUserService.findByIdIn(ids);
	}
	
	@PostMapping(value = "/accept", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceFriendReqDTO acceptFriendReq(@RequestBody FaceFriendReqDTO faceFriendReqDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		faceFriendReqDTO.setFaceFriendId(userDetails.getId());
		faceFriendReqDTO.setStatus(statusService.findById(2L));
		return faceFriendReqService.updateToAccepted(faceFriendReqDTO);
	}
	
	@PostMapping(value = "/decline", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceFriendReqDTO declineFriendReq(@RequestBody FaceFriendReqDTO faceFriendReqDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		faceFriendReqDTO.setFaceFriendId(userDetails.getId());
		faceFriendReqDTO.setStatus(statusService.findById(2L));
		return faceFriendReqService.updateToDecline(faceFriendReqDTO);
	}
	
}
