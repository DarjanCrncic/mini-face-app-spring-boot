package com.example.minifaceapp.services;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minifaceapp.dto.FaceUserDTO;
import com.example.minifaceapp.dto.SearchDTO;
import com.example.minifaceapp.mappers.FaceUserDTOMapper;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceUserRepository;
import com.example.minifaceapp.utils.ConcatSQLSearch;

@Service
@Repository
public class FaceUserServiceImpl implements FaceUserService{

	private final FaceUserRepository faceUserRepository;
	private JdbcTemplate jdbcTemplate;
	
	public FaceUserServiceImpl(FaceUserRepository faceUserRepository, JdbcTemplate jdbcTemplate) {
		this.faceUserRepository = faceUserRepository;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public FaceUser save(FaceUser faceUser) {
		return faceUserRepository.save(faceUser);
	}

	@Override
	public List<FaceUser> findAll() {
		return faceUserRepository.findAll();
	}

	@Override
	public FaceUser findById(Long id) {
		return faceUserRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(FaceUser object) {	
	}

	@Override
	public void deleteById(Long id) {
	}

	@Override
	public FaceUser findByUsername(String username) {
		return faceUserRepository.findByUsername(username);
	}

	@Override
	public FaceUser findByEmail(String email) {
		return faceUserRepository.findByEmail(email);
	}

	@Override
	public List<FaceUserDTO> searchFaceUsers(SearchDTO searchDTO, FaceUser faceUser) {
		String[] caseAll = { "FU.SURNAME", "FU.NAME" };
		System.out.println(ConcatSQLSearch.createSQLQueryAddition("and", searchDTO, caseAll));
		String placeholder = "";
		
		if(!searchDTO.getSearchWords().get(0).isBlank()) {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", searchDTO, caseAll);
		}
		String id = Long.toString(faceUser.getId());
		
		//List<FaceUser> otherUsers = faceUserRepository.searchFaceUsers();
		//System.out.println(otherUsers);
		
		
		String query = "select * from face_user fu where id not in "
				+ "(select friend_user_id from face_friend where face_user_id = ?) "
				+ "and id not in (select face_user_id from face_friend where friend_user_id = ?) and id != ? "+ placeholder
				+ " order by upper(name), upper(surname) ";
		
		return jdbcTemplate.query(query, new Object[] {id, id, id}, new int[] {Types.INTEGER, Types.INTEGER, Types.INTEGER}, new FaceUserDTOMapper());

	}	
}
