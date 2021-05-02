package com.example.minifaceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.minifaceapp.model.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>{

	@Query("select count(distinct pl.likerId) from PostLike pl where pl.postId = ?1")
	int findAllDistinctByPostId(Long id);

}
