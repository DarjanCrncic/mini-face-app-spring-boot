package com.example.minifaceapp.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FaceFriendReqDTO;
import com.example.minifaceapp.model.FaceFriendReq;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FaceFriendReqDTOMapper {

	FaceFriendReqDTOMapper INSTANCE = Mappers.getMapper(FaceFriendReqDTOMapper.class);
	
	FaceFriendReqDTO faceFriendReqToFaceFriendReqDTO(FaceFriendReq faceFriendReq);
	
	@Mapping(target = "creationTime", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	FaceFriendReq faceFriendReqDTOToFaceFriendReq(FaceFriendReqDTO faceFriendReqDTO);

}
