package com.example.minifaceapp.services;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.minifaceapp.model.FaceFriendReq;
import com.example.minifaceapp.model.Status;
import com.example.minifaceapp.repositories.FaceFriendReqRepository;
import com.example.minifaceapp.repositories.StatusRepository;

@Service
@Repository
public class FaceFriendReqServiceImpl implements FaceFriendReqService {

	FaceFriendReqRepository faceFriendReqRepostory;
	StatusRepository statusRepository;
	
	public FaceFriendReqServiceImpl(FaceFriendReqRepository faceFriendReqRepostory, StatusRepository statusRepository) {
		this.faceFriendReqRepostory = faceFriendReqRepostory;
		this.statusRepository = statusRepository;
	}

	@Override
	public List<FaceFriendReq> findAll() {
		return null;
	}

	@Override
	public FaceFriendReq findById(Long id) {
		return faceFriendReqRepostory.findById(id).orElse(null);
	}

	@Override
	public FaceFriendReq save(FaceFriendReq faceFriendReq) {
		return faceFriendReqRepostory.save(faceFriendReq);
	}

	@Override
	public void delete(FaceFriendReq object) {	
	}

	@Override
	public void deleteById(Long id) {
	}

	@Override
	public List<Long> findAllByFaceFriendId(Long id) {
		Status status = statusRepository.findById(1L).orElse(null);
		List<Long> ids = faceFriendReqRepostory.getPendingFriendRequests(id, status);
		return ids;
	}

}
