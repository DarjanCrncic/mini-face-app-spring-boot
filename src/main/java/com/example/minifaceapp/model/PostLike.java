package com.example.minifaceapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class PostLike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "post_id")
	private Long postId;
	
	@Column(name = "liker_id")
	private Long likerId;
	
	@Column(name = "creation_time")
	@CreationTimestamp
	private LocalDateTime creationTime;
	
	@Column(name = "update_time")
	@UpdateTimestamp
	private LocalDateTime updateTime;
}

