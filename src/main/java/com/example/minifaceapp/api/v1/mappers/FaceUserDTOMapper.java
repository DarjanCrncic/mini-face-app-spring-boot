package com.example.minifaceapp.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.model.FaceUser;

@Mapper(componentModel = "spring")
public interface FaceUserDTOMapper {

	FaceUserDTOMapper INSTANCE = Mappers.getMapper(FaceUserDTOMapper.class);
	
	FaceUserDTO faceUserToFaceUserDTOMapper(FaceUser faceUser);
	
	FaceUser faceUserDTOToFaceUserMapper(FaceUserDTO faceUserDTO);
	
	void updateFaceUserFromDTO(FaceUserDTO faceUserDTO, @MappingTarget FaceUser faceUser);
}
