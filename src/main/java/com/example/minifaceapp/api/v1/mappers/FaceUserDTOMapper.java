package com.example.minifaceapp.api.v1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.model.FaceUser;

@Mapper(componentModel = "spring")
public interface FaceUserDTOMapper {

	FaceUserDTOMapper INSTANCE = Mappers.getMapper(FaceUserDTOMapper.class);
	
	FaceUserDTO faceUserToFaceUserDTOMapper(FaceUser faceUser);
	
	@Mapping(target = "creationTime", ignore = true)
	@Mapping(target = "friendOf", ignore = true)
	@Mapping(target = "friends", ignore = true)
	@Mapping(target = "groups", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	FaceUser faceUserDTOToFaceUserMapper(FaceUserDTO faceUserDTO);
	
	@Mapping(target = "creationTime", ignore = true)
	@Mapping(target = "friendOf", ignore = true)
	@Mapping(target = "friends", ignore = true)
	@Mapping(target = "groups", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	void updateFaceUserFromDTO(FaceUserDTO faceUserDTO, @MappingTarget FaceUser faceUser);
	
	List<FaceUserDTO> faceUserListToFaceUserDTOList(List<FaceUser> list);
}
