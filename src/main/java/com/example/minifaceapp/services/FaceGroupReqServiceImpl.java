package com.example.minifaceapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.FaceGroupReqDTO;
import com.example.minifaceapp.api.v1.mappers.FaceGroupReqDTOMapper;
import com.example.minifaceapp.model.FaceGroupReq;
import com.example.minifaceapp.model.Status;
import com.example.minifaceapp.repositories.FaceGroupReqRepository;
import com.example.minifaceapp.repositories.StatusRepository;

@Service
public class FaceGroupReqServiceImpl implements FaceGroupReqService{
	
	private FaceGroupReqDTOMapper faceGroupReqDTOMapper;
	private FaceGroupReqRepository faceGroupReqRepository;
	private StatusRepository statusRepository;
	
	public FaceGroupReqServiceImpl(FaceGroupReqDTOMapper faceGroupReqDTOMapper, FaceGroupReqRepository faceGroupReqRepository, StatusRepository statusRepository) {
		this.faceGroupReqDTOMapper = faceGroupReqDTOMapper;
		this.faceGroupReqRepository = faceGroupReqRepository;
		this.statusRepository = statusRepository;
	}

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
	}

	@Override
	public void deleteById(Long id) {
	}

	@Override
	public List<Long> findAllByFaceUserId(Long id) {
		Status status = statusRepository.findById(1L).orElse(null);
		return faceGroupReqRepository.getPendingGroupRequests(id, status);
	}

}
