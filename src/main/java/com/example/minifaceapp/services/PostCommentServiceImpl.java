package com.example.minifaceapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.PostCommentDTO;
import com.example.minifaceapp.api.v1.mappers.FaceUserDTOMapper;
import com.example.minifaceapp.api.v1.mappers.PostCommentDTOMapper;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.model.PostComment;
import com.example.minifaceapp.repositories.PostCommentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {
	
	PostCommentRepository postCommentRepository;
	PostCommentDTOMapper postCommentDTOMapper;
	FaceUserDTOMapper faceUserDTOMapper;
	
	@Override
	public List<PostCommentDTO> findAll() {
		return new ArrayList<>();
	}

	@Override
	public PostCommentDTO findById(Long id) {
		return postCommentDTOMapper.postCommentToPostCommentDTOMapper(postCommentRepository.findById(id).orElse(null));
	}

	@Override
	public PostCommentDTO save(PostCommentDTO postCommentDTO) {
		postCommentRepository.save(postCommentDTOMapper.postCommentDTOToPostCommentMapper(postCommentDTO));
		return postCommentDTO;
	}

	@Override
	public void delete(PostCommentDTO postCommentDTO) {
		postCommentRepository.delete(postCommentDTOMapper.postCommentDTOToPostCommentMapper(postCommentDTO));	
	}

	@Override
	public void deleteById(Long id) {
		postCommentRepository.deleteById(id);
	}

	@Override
	public List<PostCommentDTO> getAllCommentsForPost(Long id) {
		ArrayList<PostComment> comments = postCommentRepository.getAllByPostIdOrderByCreationTime(id);
		return postCommentDTOMapper.postCommentListToPostCommentDTOList(comments);
	}

	@Override
	public PostCommentDTO save(PostCommentDTO postCommentDTO, FaceUser faceUser) {
		PostComment postComment = postCommentDTOMapper.postCommentDTOToPostCommentMapper(postCommentDTO);
		postComment.setFaceUser(faceUser);
		postCommentRepository.save(postComment);
		return postCommentDTO;
	}

}
