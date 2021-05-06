package com.example.minifaceapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.minifaceapp.model.FacePost;

public interface FacePostRepository extends JpaRepository<FacePost, Long>{

	@Query("select fp from FacePost fp inner join fp.creator as c where c in c.friends and c.id = ?1")
	List<FacePost> getPostsVissibleToUser(Long id);
}
