package com.example.minifaceapp.services;

import java.util.List;

import javax.validation.Valid;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.model.FaceUser;

public interface FaceUserService extends CrudService<FaceUserDTO, Long>{

	FaceUser findByUsername(String username);

	List<FaceUserDTO> searchFaceUsers(SearchDTO searchDTO, FaceUser faceUser);

	List<FaceUserDTO> findFriends(FaceUser faceUser);

	List<FaceUserDTO> findByIdIn(List<Long> ids);

	void saveUserOnRegister(@Valid FaceUser faceUser);

}
