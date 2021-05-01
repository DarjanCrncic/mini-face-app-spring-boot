package com.example.minifaceapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.FaceFriendReqDTO;
import com.example.minifaceapp.api.v1.mappers.FaceFriendReqDTOMapper;
import com.example.minifaceapp.model.FaceFriendReq;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.model.Status;
import com.example.minifaceapp.repositories.FaceFriendReqRepository;
import com.example.minifaceapp.repositories.FaceUserRepository;
import com.example.minifaceapp.repositories.StatusRepository;

@Service
@Repository
public class FaceFriendReqServiceImpl implements FaceFriendReqService {

	FaceFriendReqRepository faceFriendReqRepostory;
	StatusRepository statusRepository;
	FaceFriendReqDTOMapper faceFriendReqDTOMapper;
	FaceUserRepository faceUserRepository;
	
	public FaceFriendReqServiceImpl(FaceFriendReqRepository faceFriendReqRepostory, StatusRepository statusRepository, FaceFriendReqDTOMapper faceFriendReqDTOMapper,
			FaceUserRepository faceUserRepository) {
		this.faceFriendReqRepostory = faceFriendReqRepostory;
		this.statusRepository = statusRepository;
		this.faceFriendReqDTOMapper = faceFriendReqDTOMapper;
		this.faceUserRepository = faceUserRepository;
	}

	@Override
	public List<FaceFriendReqDTO> findAll() {
		List<FaceFriendReq> reqs = faceFriendReqRepostory.findAll();
		List<FaceFriendReqDTO> dtos = new ArrayList<>();
		for(FaceFriendReq req : reqs) {
			dtos.add(faceFriendReqDTOMapper.faceFriendReqToFaceFriendReqDTO(req));
		}
		return dtos;
	}

	@Override
	public FaceFriendReqDTO findById(Long id) {
		return faceFriendReqDTOMapper.faceFriendReqToFaceFriendReqDTO(faceFriendReqRepostory.findById(id).orElse(null));
	}

	@Override
	public FaceFriendReqDTO save(FaceFriendReqDTO faceFriendReqDTO) {
		faceFriendReqRepostory.save(faceFriendReqDTOMapper.faceFriendReqDTOToFaceFriendReq(faceFriendReqDTO));
		return faceFriendReqDTO;
	}

	@Override
	public void delete(FaceFriendReqDTO object) {	
	}

	@Override
	public void deleteById(Long id) {
	}

	@Override
	public List<Long> findAllByFaceFriendId(Long id) {
		Status status = statusRepository.findById(1L).orElse(null);
		return faceFriendReqRepostory.getPendingFriendRequests(id, status);
	}

	@Override
	public FaceFriendReqDTO updateToAccepted(FaceFriendReqDTO faceFriendReqDTO) {
		Status status = statusRepository.findById(2L).orElse(null);
		List<FaceFriendReq> reqs = faceFriendReqRepostory.findAllByFaceFriendIdAndFaceUserId(faceFriendReqDTO.getFaceFriendId(), faceFriendReqDTO.getFaceUserId());
		for(FaceFriendReq req: reqs) {
			req.setStatus(status);
			faceFriendReqRepostory.save(req);
		}
		if(!reqs.isEmpty()) {
			FaceUser currentUser = faceUserRepository.findById(faceFriendReqDTO.getFaceFriendId()).orElse(null);
			FaceUser friendUser = faceUserRepository.findById(faceFriendReqDTO.getFaceUserId()).orElse(null);
			
			currentUser.getFriends().add(friendUser);
			currentUser.getFriendOf().add(friendUser);			
			faceUserRepository.save(currentUser);
		}
		return faceFriendReqDTO;	
	}
	
	@Override
	public FaceFriendReqDTO updateToDecline(FaceFriendReqDTO faceFriendReqDTO) {
		Status status = statusRepository.findById(3L).orElse(null);
		List<FaceFriendReq> reqs = faceFriendReqRepostory.findAllByFaceFriendIdAndFaceUserId(faceFriendReqDTO.getFaceFriendId(), faceFriendReqDTO.getFaceUserId());
		for(FaceFriendReq req: reqs) {
			req.setStatus(status);
			faceFriendReqRepostory.save(req);
		}
		return faceFriendReqDTO;
	}
	
}