package com.example.minifaceapp.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

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
	
	@Column(name = "creation_time")
	@CreationTimestamp
	private LocalDateTime creationTime;
	
	@Column(name = "update_time")
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "image")
	private Blob image;
	
	@Column(name = "age")
	@ColumnDefault("0")
	private int age;
	
	@Column(name = "notify")
	@ColumnDefault("0")
	private boolean notify;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="face_friend",
	 joinColumns=@JoinColumn(name="face_user_id"),
	 inverseJoinColumns=@JoinColumn(name="friend_user_id"),
	 uniqueConstraints=@UniqueConstraint(columnNames = {"face_user_id", "friend_user_id"})
	)
	private List<FaceUser> friends;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name="face_friend",
	 joinColumns=@JoinColumn(name="friend_user_id"),
	 inverseJoinColumns=@JoinColumn(name="face_user_id"),
	 uniqueConstraints=@UniqueConstraint(columnNames = {"friend_user_id", "face_user_id"})
	)
	private List<FaceUser> friendOf;
		
}
