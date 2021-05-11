package com.example.minifaceapp.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FaceGroupReqDTO;
import com.example.minifaceapp.model.FaceGroupReq;

@Mapper(componentModel = "spring")
public interface FaceGroupReqDTOMapper {
	
	FaceGroupReqDTOMapper INSTANCE = Mappers.getMapper(FaceGroupReqDTOMapper.class);
	
	FaceGroupReqDTO faceGroupReqToFaceGroupReqDTO(FaceGroupReq faceGroupReq);
	
	@Mapping(target = "creationTime", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	FaceGroupReq faceGroupReqDTOToFaceGroupReq(FaceGroupReqDTO faceGroupReqDTO);

}
