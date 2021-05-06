package com.example.minifaceapp.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.model.FacePost;

@Mapper(componentModel = "spring", uses = FaceUserDTOMapper.class)
public interface FacePostDTOMapper {
	
	FacePostDTOMapper INSTANCE = Mappers.getMapper(FacePostDTOMapper.class);
	
	@Mapping(target = "creator", source = "creator")
	FacePostDTO facePostToFacePostDTOMapper(FacePost facePost);
	
	@Mapping(target = "updateTime", ignore = true)
	@Mapping(target = "creator", source = "creator")
	FacePost facePostDTOToFacePostMapper(FacePostDTO facePostDTO);
}
