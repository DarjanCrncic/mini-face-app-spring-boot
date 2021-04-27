package com.example.minifaceapp.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.services.FaceUserService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	private final FaceUserService faceUserService;

	public ProfileController(FaceUserService faceUserService) {
		super();
		this.faceUserService = faceUserService;
	}
	
	@GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
	public List<FaceUser> getAllUsers() {
		return faceUserService.findAll();
	}
	
	@GetMapping("/info")
	@ResponseStatus(HttpStatus.OK)
	public FaceUser getUserInfo(Principal principal) {
		FaceUser faceUser = faceUserService.findByUsername(principal.getName());
		if(faceUser != null) faceUser.setPassword(null);
		return faceUser;
	}
	
	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateUserInfo(@RequestParam Map<String, String> update, Principal principal) {
	
		FaceUser faceUser = faceUserService.findByUsername(principal.getName());
		faceUser.setSurname(update.get("surname"));
		faceUser.setName(update.get("name"));
		faceUser.setAge(Integer.parseInt(update.getOrDefault("age", "0")));
		faceUser.setNotify(Boolean.parseBoolean(update.getOrDefault("notify", "false")));
		faceUser.setCity(update.get("city"));
		faceUser.setCountry(update.get("country"));
		faceUser.setGender(update.get("gender"));
		
		faceUserService.save(faceUser);
	}
	
	
}
