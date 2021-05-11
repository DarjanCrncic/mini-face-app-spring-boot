package com.example.minifaceapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minifaceapp.model.FaceUser;

public interface FaceUserRepository extends JpaRepository<FaceUser, Long> {

	FaceUser findByUsername(String username);
	
	FaceUser findByEmail(String email);

	List<FaceUser> findByIdIn(List<Long> ids);
	
}
