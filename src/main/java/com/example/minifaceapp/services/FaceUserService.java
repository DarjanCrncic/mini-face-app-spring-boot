package com.example.minifaceapp.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.model.FaceUser;

public interface FaceUserService extends CrudService<FaceUserDTO, Long>{

	FaceUser findByUsername(String username);

	List<FaceUserDTO> findFriends(FaceUser faceUser);

	List<FaceUserDTO> findByIdIn(List<Long> ids);

	void saveUserOnRegister(@Valid FaceUser faceUser);

	List<FaceUserDTO> searchFaceUsers(SearchDTO searchDTO, Long faceUserId);

	FaceUserDTO saveImage(MultipartFile multipartFile, Long id);

	void getImage(Long id, HttpServletResponse response) throws IOException;

	String getImageAsString(Long id);

	FaceUserDTO switchNotify(boolean notify, Long id);


}
