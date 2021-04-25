package com.example.minifaceapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
public class FaceUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "username", unique = true)
	@NotEmpty(message = "must enter username")
	private String username;
	
	@Column(name = "password")
	@NotEmpty(message = "must enter password")
	private String password;
	
	@Column(name = "name")
	@NotEmpty(message = "must enter first name")
	private String name;
	
	@Column(name = "surname")
	@NotEmpty(message = "must enter last name")
	private String surname;
	
	@Column(name = "email", unique = true)
	@NotEmpty(message = "must enter email")
	@Email(message = "must be a valid email address")
	private String email;
}
