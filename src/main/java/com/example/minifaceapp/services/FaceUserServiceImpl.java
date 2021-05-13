package com.example.minifaceapp.services;

import java.sql.Types;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.api.v1.mappers.FaceUserDTOMapper;
import com.example.minifaceapp.api.v1.mappers.FaceUserDTORowMapper;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceUserRepository;
import com.example.minifaceapp.utils.ConcatSQLSearch;

@Service
public class FaceUserServiceImpl implements FaceUserService {

	private final FaceUserRepository faceUserRepository;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FaceUserDTOMapper faceUserDTOMapper;

	public FaceUserServiceImpl(FaceUserRepository faceUserRepository, JdbcTemplate jdbcTemplate,
			FaceUserDTOMapper faceUserDTOMapper) {
		this.faceUserRepository = faceUserRepository;
		this.jdbcTemplate = jdbcTemplate;
		this.faceUserDTOMapper = faceUserDTOMapper;
	}

	@Override
	@Transactional
	public FaceUserDTO save(FaceUserDTO faceUserDTO) {
		FaceUser faceUser = faceUserRepository.findById(faceUserDTO.getId()).orElse(null);
		if (faceUser != null) {
			faceUserDTO.setUsername(faceUser.getUsername());
			faceUserDTO.setEmail(faceUser.getEmail());
			faceUserDTOMapper.updateFaceUserFromDTO(faceUserDTO, faceUser);
			faceUser = faceUserRepository.save(faceUser);
		}
		return faceUserDTOMapper.faceUserToFaceUserDTOMapper(faceUser);
	}

	@Override
	public List<FaceUserDTO> findAll() {
		List<FaceUser> users = faceUserRepository.findAll();
		return faceUserDTOMapper.faceUserListToFaceUserDTOList(users);
	}

	@Override
	public FaceUserDTO findById(Long id) {
		return faceUserDTOMapper.faceUserToFaceUserDTOMapper(faceUserRepository.findById(id).orElse(null));
	}

	@Override
	public FaceUser findByUsername(String username) {
		return faceUserRepository.findByUsername(username);
	}

	@Override
	public List<FaceUserDTO> searchFaceUsers(SearchDTO searchDTO, Long faceUserId) {
		String[] caseAll = { "FU.SURNAME", "FU.NAME" };

		String placeholder = "";

		if (!searchDTO.getSearchWords().get(0).isBlank()) {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", searchDTO, caseAll);
		}
		String id = Long.toString(faceUserId);
		System.out.println(placeholder);

		String query = "select * from face_user fu where id not in "
				+ "(select friend_user_id from face_friend where face_user_id = ?) "
				+ "and id not in (select face_user_id from face_friend where friend_user_id = ?) and id != ? "
				+ placeholder + " order by upper(name), upper(surname) ";

		return jdbcTemplate.query(query, new Object[] { id, id, id },
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER }, new FaceUserDTORowMapper());

	}

	@Override
	public List<FaceUserDTO> findFriends(FaceUser faceUser) {
		List<FaceUser> users = faceUser.getFriends();
		return faceUserDTOMapper.faceUserListToFaceUserDTOList(users);
	}

	@Override
	public List<FaceUserDTO> findByIdIn(List<Long> ids) {
		List<FaceUser> users = faceUserRepository.findByIdIn(ids);
		return faceUserDTOMapper.faceUserListToFaceUserDTOList(users);
	}

	@Override
	public void saveUserOnRegister(@Valid FaceUser faceUser) {
		faceUserRepository.save(faceUser);
	}

	@Override
	public void delete(FaceUserDTO object) {
	}

	@Override
	public void deleteById(Long id) {
	}

	
}
