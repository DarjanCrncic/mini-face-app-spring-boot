package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.api.v1.dtos.PostCommentDTO;
import com.example.minifaceapp.model.FaceUser;

public interface PostCommentService extends CrudService<PostCommentDTO, Long>{

	List<PostCommentDTO> getAllCommentsForPost(Long id);

	PostCommentDTO save(PostCommentDTO postCommentDTO, FaceUser findByUsername);

}
