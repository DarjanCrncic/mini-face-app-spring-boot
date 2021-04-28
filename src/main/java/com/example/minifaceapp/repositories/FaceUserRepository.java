package com.example.minifaceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minifaceapp.model.FaceUser;

public interface FaceUserRepository extends JpaRepository<FaceUser, Long> {

	FaceUser findByUsername(String username);
	
	FaceUser findByEmail(String email);

}
