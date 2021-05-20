package com.example.minifaceapp.controllers;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.minifaceapp.api.v1.mappers.FaceUserDTOMapper;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.services.FaceUserService;

@Controller
public class NavigationController {

	private static final String REGISTER_USER = "register_user";
	private final FaceUserService faceUserService;
	private final FaceUserDTOMapper faceUserDTOMapper;

	public NavigationController(FaceUserService faceUserService, FaceUserDTOMapper faceUserDTOMapper) {
		this.faceUserService = faceUserService;
		this.faceUserDTOMapper = faceUserDTOMapper;
	}

	@RequestMapping({ "/", "/login" })
	public String showLoginPage() {
		return "login_user";
	}

	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new FaceUser());
		return REGISTER_USER;
	}

	@PostMapping("/register")
	public String handleRegisterPage(@Valid @ModelAttribute("faceUser") FaceUser faceUser, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("faceUser", faceUser);
			model.addAttribute("errors", result.getAllErrors());
			return REGISTER_USER;
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(faceUser.getPassword());
		
		if (faceUser.getUsername().contains(" ")) {
			model.addAttribute("constraintError", "Username contains invalid characters");
			return REGISTER_USER;
		}

		if (faceUserService.findByUsername(faceUser.getUsername()) != null) {
			model.addAttribute("constraintError", "Already used username");
			return REGISTER_USER;
		}

		faceUser.setPassword(encodedPassword);
		faceUserService.saveUserOnRegister(faceUser);

		model.addAttribute("user", faceUserDTOMapper.faceUserToFaceUserDTOMapper(faceUser));
		return "login_user";
	}

	@GetMapping("/main")
	public String showMainPage(Model model) {
		return "main";
	}
	
}
