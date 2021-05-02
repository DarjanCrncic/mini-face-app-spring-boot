package com.example.minifaceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.minifaceapp.model.PostType;
import com.example.minifaceapp.repositories.PostTypeRepository;

@Service
public class PostTypeServiceImpl implements PostTypeService{
	
	PostTypeRepository postTypeRepository;
	
	public PostTypeServiceImpl(PostTypeRepository postTypeRepository) {
		this.postTypeRepository = postTypeRepository;
	}

	@Override
	public List<PostType> findAll() {
		return postTypeRepository.findAll();
	}

	@Override
	public PostType findById(Long id) {
		return postTypeRepository.findById(id).orElse(null);
	}

	@Override
	public PostType save(PostType postType) {
		return postTypeRepository.save(postType);
	}

	@Override
	public void delete(PostType postType) {
		postTypeRepository.delete(postType);
	}

	@Override
	public void deleteById(Long id) {
		postTypeRepository.deleteById(id);	
	}

}
