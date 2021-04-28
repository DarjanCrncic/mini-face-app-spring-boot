package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.dto.FaceUserDTO;
import com.example.minifaceapp.dto.SearchDTO;
import com.example.minifaceapp.model.FaceUser;

public interface FaceUserService extends CrudService<FaceUser, Long>{

	FaceUser findByUsername(String username);

	FaceUser findByEmail(String email);

	List<FaceUserDTO> searchFaceUsers(SearchDTO searchDTO, FaceUser faceUser);

	List<FaceUserDTO> findFriends(FaceUser faceUser);

}
