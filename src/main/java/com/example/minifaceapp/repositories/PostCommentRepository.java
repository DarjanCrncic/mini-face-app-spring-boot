package com.example.minifaceapp.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minifaceapp.model.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

	ArrayList<PostComment> getAllByPostIdOrderByCreationTime(Long id);

}
