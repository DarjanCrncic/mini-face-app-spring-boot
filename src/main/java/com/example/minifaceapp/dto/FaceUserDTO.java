package com.example.minifaceapp.dto;

import lombok.Data;

@Data
public class FaceUserDTO {

	private Long id;
	private String name;
	private String surname;
	private String city;
	private String country;
	private String gender;
	private int age;
	private String email;
	private String username;
}
