package com.example.minifaceapp.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FaceFriendReqDTO;
import com.example.minifaceapp.model.FaceFriendReq;

@Mapper
public interface FaceFriendReqDTOMapper {

	FaceFriendReqDTOMapper INSTANCE = Mappers.getMapper(FaceFriendReqDTOMapper.class);
	
	FaceFriendReqDTO faceFriendReqToFaceFriendReqDTO(FaceFriendReq faceFriendReq);

}
