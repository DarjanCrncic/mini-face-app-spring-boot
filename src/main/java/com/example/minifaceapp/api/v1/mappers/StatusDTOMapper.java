package com.example.minifaceapp.api.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.StatusDTO;
import com.example.minifaceapp.model.Status;

@Mapper
public interface StatusDTOMapper {
	
	StatusDTOMapper INSTANCE = Mappers.getMapper(StatusDTOMapper.class);
	
	StatusDTO statusToStatusDTOMapper(Status status);
	
	Status statusDTOToStatusMapper(StatusDTO statusDTO);
}
