package com.example.minifaceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.minifaceapp.model.PostLike;
import com.example.minifaceapp.repositories.PostLikeRepository;

@Service
public class PostLikeServiceImpl implements PostLikeService{
	
	PostLikeRepository postLikeRepository;
	
	public PostLikeServiceImpl(PostLikeRepository postLikeRepository) {
		this.postLikeRepository = postLikeRepository;
	}

	@Override
	public List<PostLike> findAll() {
		return postLikeRepository.findAll();
	}

	@Override
	public PostLike findById(Long id) {
		return postLikeRepository.findById(id).orElse(null);
	}

	@Override
	public PostLike save(PostLike postLike) {
		return postLikeRepository.save(postLike);
	}

	@Override
	public void delete(PostLike postLike) {
		postLikeRepository.delete(postLike);
	}

	@Override
	public void deleteById(Long id) {
		postLikeRepository.deleteById(id);
	}

	@Override
	public int getPostLikeCount(Long id) {
		return postLikeRepository.findAllDistinctByPostId(id);
	}

}
