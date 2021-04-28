package com.example.minifaceapp.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.dto.FaceUserDTO;
import com.example.minifaceapp.dto.SearchDTO;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.services.FaceUserService;

@RestController
@RequestMapping("/friends")
public class FriendsController {
	
	private final FaceUserService faceUserService;
	
	public FriendsController(FaceUserService faceUserService) {
		this.faceUserService = faceUserService;
	}

	@GetMapping("/find")
	public List<FaceUser> findFriends(){
		return null;
		
	}
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FaceUserDTO> searchFriends(@RequestBody SearchDTO searchDTO, Principal principal){
		System.out.println(searchDTO.toString());
		FaceUser faceUser = faceUserService.findByUsername(principal.getName());
		return faceUserService.searchFaceUsers(searchDTO, faceUser);
	}
	

}
