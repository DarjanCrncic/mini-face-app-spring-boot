package com.example.minifaceapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class PostComment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "body")
	private String body;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private FaceUser faceUser;
	
	@Column(name = "post_id")
	private Long postId;
	
	@Column(name = "creation_time")
	@CreationTimestamp
	private LocalDateTime creationTime;
	
	@Column(name = "update_time")
	@UpdateTimestamp
	private LocalDateTime updateTime;
}
