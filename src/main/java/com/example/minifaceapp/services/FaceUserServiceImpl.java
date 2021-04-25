package com.example.minifaceapp.services;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceUserRepository;

@Service
@Repository
public class FaceUserServiceImpl implements FaceUserService{

	private final FaceUserRepository faceUserRepository;

	public FaceUserServiceImpl(FaceUserRepository faceUserRepository) {
		this.faceUserRepository = faceUserRepository;
	}

	@Override
	@Transactional
	public FaceUser save(FaceUser faceUser) {
		return faceUserRepository.save(faceUser);
	}

	@Override
	public Set<FaceUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FaceUser findById(Long id) {
		return faceUserRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(FaceUser object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FaceUser findByUsername(String username) {
		return faceUserRepository.findByUsername(username);
	}

	@Override
	public FaceUser findByEmail(String email) {
		return faceUserRepository.findByEmail(email);
	}	
	
}
