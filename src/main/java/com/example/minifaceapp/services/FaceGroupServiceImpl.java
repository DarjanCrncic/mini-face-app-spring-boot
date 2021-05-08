package com.example.minifaceapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.FaceGroupDTO;
import com.example.minifaceapp.api.v1.mappers.FaceGroupDTOMapper;
import com.example.minifaceapp.api.v1.mappers.FaceUserDTOMapper;
import com.example.minifaceapp.model.FaceGroup;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceGroupRepository;
import com.example.minifaceapp.repositories.FaceUserRepository;

@Service
public class FaceGroupServiceImpl implements FaceGroupService{
	
	FaceGroupRepository faceGroupRepository;
	FaceGroupDTOMapper faceGroupDTOMapper;
	FaceUserRepository faceUserRepository;
	private FaceUserDTOMapper faceUserDTOMapper;

	public FaceGroupServiceImpl(FaceGroupRepository faceGroupRepository, FaceGroupDTOMapper faceGroupDTOMapper,
			FaceUserRepository faceUserRepository, FaceUserDTOMapper faceUserDTOMapper) {
		this.faceGroupRepository = faceGroupRepository;
		this.faceGroupDTOMapper = faceGroupDTOMapper;
		this.faceUserRepository = faceUserRepository;
		this.faceUserDTOMapper = faceUserDTOMapper;
	}

	@Override
	public List<FaceGroupDTO> findAll() {
		return new ArrayList<>();
	}

	@Override
	public FaceGroupDTO findById(Long id) {
		return faceGroupDTOMapper.faceGroupToFaceGroupDTO(faceGroupRepository.findById(id).orElse(null));
	}

	@Override
	public FaceGroupDTO save(FaceGroupDTO faceGroupDTO) {
		FaceGroup saved = faceGroupRepository.save(faceGroupDTOMapper.faceGroupDTOToFaceGroup(faceGroupDTO));
		return faceGroupDTOMapper.faceGroupToFaceGroupDTO(saved);
	}

	@Override
	public void delete(FaceGroupDTO faceGroupDTO) {
		faceGroupRepository.delete(faceGroupDTOMapper.faceGroupDTOToFaceGroup(faceGroupDTO));
	}

	@Override
	public void deleteById(Long id) {
		faceGroupRepository.deleteById(id);
	}

	@Override
	public List<FaceGroupDTO> getGroupsFromUser(FaceUser faceUser) {
		return faceGroupDTOMapper.faceGroupListToFaceGroupDTOList(faceUser.getGroups());
	}

	@Override
	public FaceGroupDTO saveNew(FaceGroupDTO faceGroupDTO, FaceUser owner) {
		faceGroupDTO.setOwner(faceUserDTOMapper.faceUserToFaceUserDTOMapper(owner));
		faceGroupDTO.setMembers(new ArrayList<>());
		faceGroupDTO.getMembers().add(faceUserDTOMapper.faceUserToFaceUserDTOMapper(owner));
		faceGroupDTO = this.save(faceGroupDTO);
		
		owner.getGroups().add(faceGroupRepository.findById(faceGroupDTO.getId()).orElse(null));
		faceUserRepository.save(owner);
		return faceGroupDTO;
	}

}
