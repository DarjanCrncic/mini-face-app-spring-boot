package com.example.minifaceapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class FacePost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	@Column(name = "title")
	private String title;	
	
	@NotEmpty
	@Column(name = "body")
	private String body;
	
	@OneToOne
	@JoinColumn(name = "type", referencedColumnName = "id")
	private PostType type;
	
	@Column(name = "creation_time")
	@CreationTimestamp
	private LocalDateTime creationTime;
	
	@Column(name = "update_time")
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
	@ManyToOne
	@JoinColumn(name="creator_id")
	private FaceUser creator;
}
