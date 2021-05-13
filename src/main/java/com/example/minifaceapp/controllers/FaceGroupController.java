package com.example.minifaceapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.api.v1.dtos.FaceGroupDTO;
import com.example.minifaceapp.api.v1.dtos.FaceGroupReqDTO;
import com.example.minifaceapp.api.v1.dtos.FaceGroupSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceGroupViewDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FaceGroupReqService;
import com.example.minifaceapp.services.FaceGroupService;
import com.example.minifaceapp.services.FaceUserService;
import com.example.minifaceapp.services.StatusService;

@RestController
@RequestMapping("/groups")
public class FaceGroupController {
	
	private FaceGroupService faceGroupService;
	private FaceUserService faceUserService;
	private StatusService statusService;
	private FaceGroupReqService faceGroupReqService;

	public FaceGroupController(FaceGroupService faceGroupService, FaceUserService faceUserService, StatusService statusService, FaceGroupReqService faceGroupReqService) {
		this.faceGroupService = faceGroupService;
		this.faceUserService = faceUserService;
		this.statusService = statusService;
		this.faceGroupReqService = faceGroupReqService;
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
	
	@PostMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceGroupDTO editGroup(@RequestBody FaceGroupDTO faceGroupDTO, @PathVariable Long id) {		
		faceGroupDTO.setId(id);
		return faceGroupService.save(faceGroupDTO);
	}
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceGroupSearchDTO> searchAllPosts(@RequestBody SearchDTO searchDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {		
		return faceGroupService.searchGroups(searchDTO, userDetails.getId());
	}
	
	@GetMapping(value = "/members/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> getGroupMembers(@PathVariable Long id){
		return faceGroupService.findById(id).getMembers();
	}
	
	@GetMapping(value = "/non-members/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceUserDTO> findFriendsNotMembers(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
		return faceGroupService.findFriendsNotMembers(userDetails.getId(), id);
	}
	
	@PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceGroupReqDTO sendGroupReq(@RequestBody FaceGroupReqDTO faceGroupReqDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		faceGroupReqDTO.setStatus(statusService.findById(1L));
		return faceGroupReqService.save(faceGroupReqDTO);	
	}
	
	@GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FaceGroupDTO> getPendingReqs(@AuthenticationPrincipal CustomUserDetails userDetails){
		List<Long> ids = faceGroupReqService.findAllByFaceUserId(userDetails.getId());		
		return faceGroupService.findByIdIn(ids);
	}
	
	@PostMapping(value = "/accept", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceGroupReqDTO acceptFriendReq(@RequestBody FaceGroupReqDTO faceGroupReqDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		faceGroupReqDTO.setUserId(userDetails.getId());
		faceGroupReqDTO.setStatus(statusService.findById(2L));
		return faceGroupReqService.updateToAccepted(faceGroupReqDTO);
	}
	
	@PostMapping(value = "/decline", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceGroupReqDTO declineFriendReq(@RequestBody FaceGroupReqDTO faceGroupReqDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		faceGroupReqDTO.setUserId(userDetails.getId());
		faceGroupReqDTO.setStatus(statusService.findById(2L));
		return faceGroupReqService.updateToDecline(faceGroupReqDTO);
	}
	
	@GetMapping(value = "/posts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceGroupViewDTO getGroupDetailsAndPosts(@PathVariable Long id) {
		return faceGroupService.getGroupDetailsAndPosts(id);
	}
	
}
