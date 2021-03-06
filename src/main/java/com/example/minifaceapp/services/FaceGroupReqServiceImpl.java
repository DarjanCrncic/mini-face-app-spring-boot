package com.example.minifaceapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.FaceGroupReqDTO;
import com.example.minifaceapp.api.v1.mappers.FaceGroupReqDTOMapper;
import com.example.minifaceapp.model.FaceGroup;
import com.example.minifaceapp.model.FaceGroupReq;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.model.Status;
import com.example.minifaceapp.repositories.FaceGroupRepository;
import com.example.minifaceapp.repositories.FaceGroupReqRepository;
import com.example.minifaceapp.repositories.FaceUserRepository;
import com.example.minifaceapp.repositories.StatusRepository;
import com.example.minifaceapp.utils.exception.GroupNotFoundException;
import com.example.minifaceapp.utils.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FaceGroupReqServiceImpl implements FaceGroupReqService{
	
	private FaceGroupReqDTOMapper faceGroupReqDTOMapper;
	private FaceGroupReqRepository faceGroupReqRepository;
	private StatusRepository statusRepository;
	private FaceUserRepository faceUserRepository;
	private FaceGroupRepository faceGroupRepository;
	
	@Override
	public List<FaceGroupReqDTO> findAll() {
		return new ArrayList<>();
	}

	@Override
	public FaceGroupReqDTO findById(Long id) {
		return faceGroupReqDTOMapper.faceGroupReqToFaceGroupReqDTO(faceGroupReqRepository.findById(id).orElse(null));
	}

	@Override
	public FaceGroupReqDTO save(FaceGroupReqDTO faceGroupReqDTO) {
		FaceGroupReq saved = faceGroupReqRepository.save(faceGroupReqDTOMapper.faceGroupReqDTOToFaceGroupReq(faceGroupReqDTO));
		return faceGroupReqDTOMapper.faceGroupReqToFaceGroupReqDTO(saved);
	}

	@Override
	public void delete(FaceGroupReqDTO object) {
		// not implemented
	}

	@Override
	public void deleteById(Long id) {
		// not implemented
	}

	@Override
	public List<Long> findAllByFaceUserId(Long id) {
		Status status = statusRepository.findById(1L).orElse(null);
		return faceGroupReqRepository.getPendingGroupRequests(id, status);
	}

	@Override
	public FaceGroupReqDTO updateToAccepted(FaceGroupReqDTO faceGroupReqDTO) {
		Status status = statusRepository.findById(2L).orElse(null);
		List<FaceGroupReq> reqs = faceGroupReqRepository.findAllByUserIdAndGroupId(faceGroupReqDTO.getUserId(), faceGroupReqDTO.getGroupId());
		for(FaceGroupReq req: reqs) {
			req.setStatus(status);
			faceGroupReqRepository.save(req);
		}
		if(!reqs.isEmpty()) {
			FaceGroup faceGroup = faceGroupRepository.findById(faceGroupReqDTO.getGroupId()).orElse(null);
			FaceUser faceUser = faceUserRepository.findById(faceGroupReqDTO.getUserId()).orElse(null);
			
			if (faceUser == null) {
            	throw new UserNotFoundException();
            }
			
			if (faceGroup == null) {
            	throw new GroupNotFoundException();
            }
			
			faceUser.getGroups().add(faceGroup);	
			faceGroup.getMembers().add(faceUser);
			
			faceGroupRepository.save(faceGroup);
			faceUserRepository.save(faceUser);
		}
		return faceGroupReqDTO;	
	}

	@Override
	public FaceGroupReqDTO updateToDecline(FaceGroupReqDTO faceGroupReqDTO) {
		Status status = statusRepository.findById(3L).orElse(null);
		List<FaceGroupReq> reqs = faceGroupReqRepository.findAllByUserIdAndGroupId(faceGroupReqDTO.getUserId(), faceGroupReqDTO.getGroupId());
		for(FaceGroupReq req: reqs) {
			req.setStatus(status);
			faceGroupReqRepository.save(req);
		}
		return faceGroupReqDTO;	
	}

}
