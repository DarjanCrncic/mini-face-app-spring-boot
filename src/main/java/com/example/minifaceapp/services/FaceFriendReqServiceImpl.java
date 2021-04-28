package com.example.minifaceapp.services;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.minifaceapp.model.FaceFriendReq;
import com.example.minifaceapp.repositories.FaceFriendReqRepository;

@Service
@Repository
public class FaceFriendReqServiceImpl implements FaceFriendReqService {

	FaceFriendReqRepository faceFriendReqRepostory;
	
	public FaceFriendReqServiceImpl(FaceFriendReqRepository faceFriendReqRepostory) {
		this.faceFriendReqRepostory = faceFriendReqRepostory;
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

}
