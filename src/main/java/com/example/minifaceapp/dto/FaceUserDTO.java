package com.example.minifaceapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
