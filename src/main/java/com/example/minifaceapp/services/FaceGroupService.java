package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.api.v1.dtos.FaceGroupDTO;
import com.example.minifaceapp.api.v1.dtos.FaceGroupSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.model.FaceUser;

public interface FaceGroupService extends CrudService<FaceGroupDTO, Long> {

	List<FaceGroupDTO> getGroupsFromUser(FaceUser faceUser);

	FaceGroupDTO saveNew(FaceGroupDTO faceGroupDTO, FaceUser owner);

	List<FaceGroupSearchDTO> searchGroups(SearchDTO searchDTO, Long id);

	List<FaceUserDTO> findFriendsNotMembers(Long userId, Long groupId);

	List<FaceGroupDTO> findByIdIn(List<Long> ids);

}
