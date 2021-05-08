package com.example.minifaceapp.api.v1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.FaceGroupDTO;
import com.example.minifaceapp.model.FaceGroup;

@Mapper(componentModel = "spring", uses = FaceUserDTOMapper.class)
public interface FaceGroupDTOMapper {

	FaceGroupDTOMapper INSTANCE = Mappers.getMapper(FaceGroupDTOMapper.class);
	
	@Mapping(target = "members", source = "members")
	@Mapping(target = "owner", source = "owner")
	FaceGroupDTO faceGroupToFaceGroupDTO(FaceGroup faceGroup);
	
	@Mapping(target = "members", source = "members")
	@Mapping(target = "owner", source = "owner")
	@Mapping(target = "creationTime", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	FaceGroup faceGroupDTOToFaceGroup(FaceGroupDTO faceGroupDTO);
	
	List<FaceGroupDTO> faceGroupListToFaceGroupDTOList(List<FaceGroup> list);
}
