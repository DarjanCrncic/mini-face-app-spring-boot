package com.example.minifaceapp.services;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.FaceGroupDTO;
import com.example.minifaceapp.api.v1.dtos.FaceGroupSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceGroupViewDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.api.v1.mappers.FaceGroupDTOMapper;
import com.example.minifaceapp.api.v1.mappers.FaceGroupDTORowMapper;
import com.example.minifaceapp.api.v1.mappers.FaceUserDTOMapper;
import com.example.minifaceapp.model.FaceGroup;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceGroupRepository;
import com.example.minifaceapp.repositories.FaceUserRepository;
import com.example.minifaceapp.utils.ConcatSQLSearch;
import com.example.minifaceapp.utils.QueryHolder;
import com.example.minifaceapp.utils.exception.GroupNotFoundException;
import com.example.minifaceapp.utils.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FaceGroupServiceImpl implements FaceGroupService{
	
	FaceGroupRepository faceGroupRepository;
	FaceGroupDTOMapper faceGroupDTOMapper;
	FaceUserRepository faceUserRepository;
	FaceUserDTOMapper faceUserDTOMapper;
	JdbcTemplate jdbcTemplate;
	QueryHolder queryHolder;

	@Override
	public List<FaceGroupDTO> findAll() {
		return new ArrayList<>();
	}

	@Override
	public FaceGroupDTO findById(Long id) {
		return faceGroupDTOMapper.faceGroupToFaceGroupDTO(faceGroupRepository.findById(id).orElse(null));
	}

	@Override
	public FaceGroupDTO save(FaceGroupDTO faceGroupDTO) {
		FaceGroup oldGroup = faceGroupRepository.findById(faceGroupDTO.getId()).orElse(null);
		
		if(oldGroup == null) {
        	throw new GroupNotFoundException();
        }
		
		oldGroup.setName(faceGroupDTO.getName());
		oldGroup.setDescription(faceGroupDTO.getDescription());
		faceGroupRepository.save(oldGroup);
		return faceGroupDTOMapper.faceGroupToFaceGroupDTO(oldGroup);
	}

	@Override
	public void delete(FaceGroupDTO faceGroupDTO) {
		faceGroupRepository.delete(faceGroupDTOMapper.faceGroupDTOToFaceGroup(faceGroupDTO));
	}

	@Override
	public void deleteById(Long id) {
		faceGroupRepository.deleteById(id);
	}

	@Override
	public List<FaceGroupDTO> getGroupsFromUser(FaceUser faceUser) {
		return faceGroupDTOMapper.faceGroupListToFaceGroupDTOList(faceUser.getGroups());
	}

	@Override
	public FaceGroupDTO saveNew(FaceGroupDTO faceGroupDTO, FaceUser owner) {
		FaceGroup newfaceGroup = new FaceGroup();

		newfaceGroup.setOwner(owner);
		newfaceGroup.setMembers(new ArrayList<>());
		newfaceGroup.getMembers().add(owner);
		newfaceGroup.setName(faceGroupDTO.getName());
		newfaceGroup.setDescription(faceGroupDTO.getDescription());
		
		newfaceGroup = faceGroupRepository.save(newfaceGroup);
		
		owner.getGroups().add(newfaceGroup);
		faceUserRepository.save(owner);
		return faceGroupDTO;
	}

	@Override
	public List<FaceGroupSearchDTO> searchGroups(SearchDTO searchDTO, Long faceUserId) {
		String[] caseAll = { "FG.NAME", "(FU.NAME || ' ' || FU.SURNAME)" };
		String placeholder = "";
		if (!searchDTO.getSearchWords().get(0).trim().isEmpty()) {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", searchDTO, caseAll);
		}
		String id = Long.toString(faceUserId);
	
		String query =  queryHolder.getQueries().get("search-groups") + placeholder + " order by fg.name";
		return jdbcTemplate.query(query, new Object[] { id }, new int[] { Types.INTEGER },
				new FaceGroupDTORowMapper());
	}
	
	@Override
	public List<FaceUserDTO> findFriendsNotMembers(Long userId, Long groupId){
		FaceUser faceUser = faceUserRepository.findById(userId).orElse(null);
		FaceGroup faceGroup = faceGroupRepository.findById(groupId).orElse(null);
		
		if (faceUser == null) {
        	throw new UserNotFoundException();
        }
		
		if (faceGroup == null) {
        	throw new GroupNotFoundException();
        }
		
		List <FaceUser> notMembers = faceUser.getFriends();
		List<FaceUser> members = faceGroup.getMembers();
			
		for(FaceUser member: members) {
			notMembers.remove(member);
		}
		return faceUserDTOMapper.faceUserListToFaceUserDTOList(notMembers);
	}

	@Override
	public List<FaceGroupDTO> findByIdIn(List<Long> ids) {
		List<FaceGroup> groups = faceGroupRepository.findByIdIn(ids);
		return faceGroupDTOMapper.faceGroupListToFaceGroupDTOList(groups);
	}

	@Override
	public FaceGroupViewDTO getGroupDetailsAndPosts(Long id) {
		FaceGroupViewDTO faceGroupViewDTO = faceGroupDTOMapper.faceGroupToFaceGroupViewDTO(faceGroupRepository.findById(id).orElse(null));
		if(faceGroupViewDTO == null) throw new GroupNotFoundException();
		return faceGroupViewDTO;
	}
	
}
