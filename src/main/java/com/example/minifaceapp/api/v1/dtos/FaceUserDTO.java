package com.example.minifaceapp.api.v1.dtos;

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
	private Boolean notify;
	private String image;
}
