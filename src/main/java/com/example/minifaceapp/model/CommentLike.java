package com.example.minifaceapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "comment_like", uniqueConstraints = {@UniqueConstraint(columnNames = {"comment_id", "liker_id"})})
public class CommentLike {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "comment_id")
	private Long commentId;
	
	@Column(name = "liker_id")
	private Long likerId;
	
	@Column(name = "creation_time")
	@CreationTimestamp
	private LocalDateTime creationTime;
	
	@Column(name = "update_time")
	@UpdateTimestamp
	private LocalDateTime updateTime;
}
