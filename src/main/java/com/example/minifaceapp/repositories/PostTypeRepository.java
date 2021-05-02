package com.example.minifaceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minifaceapp.model.PostType;

public interface PostTypeRepository extends JpaRepository<PostType, Long> {

}
