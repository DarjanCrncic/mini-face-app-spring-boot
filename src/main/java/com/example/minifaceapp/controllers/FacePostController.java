package com.example.minifaceapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FacePostService;
import com.example.minifaceapp.services.FaceUserService;
import com.example.minifaceapp.services.PostTypeService;

@RestController
@RequestMapping("/posts")
public class FacePostController {
	
	private final FaceUserService faceUserService;
	private final PostTypeService postTypeService;
	private final FacePostService facePostService;

	public FacePostController(FaceUserService faceUserService, FacePostService facePostService, PostTypeService postTypeService) {
		this.faceUserService = faceUserService;
		this.facePostService = facePostService;
		this.postTypeService = postTypeService;
	}

	@PostMapping(value = "/new/user", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FacePostDTO createNewUserPost(@RequestBody FacePostDTO facePostDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		facePostDTO.setCreator(faceUserService.findById(userDetails.getId()));
		facePostDTO.setType(postTypeService.findById(1L));
		return facePostService.save(facePostDTO);
	}
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FacePostSearchDTO> searchAllPosts(@RequestBody SearchDTO searchDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		return facePostService.searchVissiblePosts(searchDTO, userDetails.getId());
	}
	
	@DeleteMapping(value = "/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePost(@PathVariable Long id) {
		facePostService.deleteById(id);
	}
	
	@PatchMapping(value = "edit/{id}")
	@ResponseStatus(HttpStatus.OK)
	public FacePostDTO editPost(@PathVariable Long id, @RequestBody FacePostDTO facePostDTO) {
		return facePostService.edit(id, facePostDTO);
	}
}