package com.example.minifaceapp.api.v1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FaceFriendReqDTO;
import com.example.minifaceapp.model.FaceFriendReq;

@Mapper(componentModel = "spring")
public interface FaceFriendReqDTOMapper {

	FaceFriendReqDTOMapper INSTANCE = Mappers.getMapper(FaceFriendReqDTOMapper.class);
	
	FaceFriendReqDTO faceFriendReqToFaceFriendReqDTO(FaceFriendReq faceFriendReq);
	
	@Mapping(target = "creationTime", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	FaceFriendReq faceFriendReqDTOToFaceFriendReq(FaceFriendReqDTO faceFriendReqDTO);
	
	List<FaceFriendReqDTO> faceFriendReqListToFaceFriendReqDTOList(List<FaceFriendReq> list);
}
