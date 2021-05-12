package com.example.minifaceapp.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class FaceGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "name")
	private String name;
	
	@NotBlank
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private FaceUser owner;
	
	@ManyToMany(mappedBy = "groups")
	private List<FaceUser> members;
	
	@Column(name = "creation_time")
	@CreationTimestamp
	private LocalDateTime creationTime;
	
	@Column(name = "update_time")
	@UpdateTimestamp
	private LocalDateTime updateTime;
	
	@OrderBy("creationTime DESC")
	@OneToMany
    @JoinTable(
            name="FACE_GROUP_POSTS",
            joinColumns = @JoinColumn( name="group_id"),
            inverseJoinColumns = @JoinColumn( name="post_id")
    )
	private List<FacePost> posts;
}
