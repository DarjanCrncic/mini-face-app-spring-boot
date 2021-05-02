package com.example.minifaceapp.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.model.FacePost;

@Mapper(componentModel = "spring")
public interface FacePostDTOMapper {
	
	FacePostDTOMapper INSTANCE = Mappers.getMapper(FacePostDTOMapper.class);
	
	FacePostDTO facePostToFacePostDTOMapper(FacePost facePost);
	
	@Mapping(target = "creationTime", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	FacePost facePostDTOToFacePostMapper(FacePostDTO facePostDTO);
}
