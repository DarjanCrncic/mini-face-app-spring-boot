package com.example.minifaceapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.mappers.FaceUserListDTO;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FaceUserService;

@RestController
@RequestMapping("/profile")
public class UserController {
	
	private final FaceUserService faceUserService;

	public UserController(FaceUserService faceUserService) {
		this.faceUserService = faceUserService;
	}
	
	@GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
	public FaceUserListDTO getAllUsers() {
		return new FaceUserListDTO(faceUserService.findAll());
	}
	
	@GetMapping("/info")
	@ResponseStatus(HttpStatus.OK)
	public FaceUserDTO getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
		return faceUserService.findById(userDetails.getId());	
	}
	
	@PostMapping(path = "/update",  consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FaceUserDTO updateUserInfo(@RequestBody FaceUserDTO faceUserDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		faceUserDTO.setId(userDetails.getId());		
		return faceUserService.save(faceUserDTO);
	}
	
}
