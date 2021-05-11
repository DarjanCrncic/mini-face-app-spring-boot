package com.example.minifaceapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minifaceapp.model.FaceGroup;

public interface FaceGroupRepository extends JpaRepository<FaceGroup, Long> {

	List<FaceGroup> findByIdIn(List<Long> ids);

}
