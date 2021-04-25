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

import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.services.FaceUserService;

@Controller
public class UserController {

	private final FaceUserService faceUserService;

	public UserController(FaceUserService faceUserService) {
		this.faceUserService = faceUserService;
	}

	@RequestMapping({ "/", "/login" })
	public String showLoginPage() {
		return "login_user";
	}

	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("user", new FaceUser());
		return "register_user";
	}

	@PostMapping("/register")
	public String handleRegisterPage(@Valid @ModelAttribute("faceUser") FaceUser faceUser, BindingResult result,
			Model model) {

		System.out.println(faceUser.toString());
		if (result.hasErrors()) {
			model.addAttribute("faceUser", faceUser);
			model.addAttribute("errors", result.getAllErrors());
			return "register_user";
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(faceUser.getPassword());

		if (faceUserService.findByUsername(faceUser.getUsername()) != null) {
			model.addAttribute("constraintError", "Already used username");
			return "register_user";
		}
		if (faceUserService.findByEmail(faceUser.getEmail()) != null) {
			model.addAttribute("constraintError", "Already used email");
			return "register_user";
		}

		faceUser.setPassword(encodedPassword);
		faceUserService.save(faceUser);

		model.addAttribute("user", faceUser);
		return "login_user";
	}

//	@PostMapping("/login")
//	public String handleLogin(Model model) {
//		return "";
//	}

	@GetMapping("/main")
	public String showMainPage(Model model) {
		return "main";
	}
	
}
