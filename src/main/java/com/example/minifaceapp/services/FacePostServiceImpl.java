package com.example.minifaceapp.services;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.api.v1.mappers.FacePostDTOMapper;
import com.example.minifaceapp.api.v1.mappers.FacePostDTORowMapper;
import com.example.minifaceapp.model.FacePost;
import com.example.minifaceapp.repositories.FacePostRepository;
import com.example.minifaceapp.utils.ConcatSQLSearch;

@Service
public class FacePostServiceImpl implements FacePostService{
	
	FacePostRepository facePostRepository;
	FacePostDTOMapper facePostDTOMapper;
	JdbcTemplate jdbcTemplate;
	
	public FacePostServiceImpl(FacePostRepository facePostRepository, FacePostDTOMapper facePostDTOMapper, JdbcTemplate jdbcTemplate) {
		this.facePostRepository = facePostRepository;
		this.facePostDTOMapper = facePostDTOMapper;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<FacePostDTO> findAll() {
		return null;
	}

	@Override
	public FacePostDTO findById(Long id) {
		return facePostDTOMapper.facePostToFacePostDTOMapper(facePostRepository.findById(id).orElse(null));
	}

	@Override
	public FacePostDTO save(FacePostDTO facePostDTO) {
		facePostRepository.save(facePostDTOMapper.facePostDTOToFacePostMapper(facePostDTO));
		return facePostDTO;
	}

	@Override
	public void delete(FacePostDTO facePostDTO) {
		facePostRepository.delete(facePostDTOMapper.facePostDTOToFacePostMapper(facePostDTO));
	}

	@Override
	public void deleteById(Long id) {
		facePostRepository.deleteById(id);
	}

	@Override
	public List<FacePostSearchDTO> searchVissiblePosts(SearchDTO searchDTO, Long faceUserId) {
		String[] caseAll = { "FP.TITLE", "FP.BODY", "FP.TYPE", "(FU.NAME || ' ' || FU.SURNAME)" };
		String placeholder = "";
		if(!searchDTO.getSearchWords().get(0).isBlank()) {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", searchDTO, caseAll);
		}
		String id = Long.toString(faceUserId);		
		System.out.println(placeholder);
		
		String query = "select fp.id, fp.title, fp.body, (fu.name || ' ' || fu.surname) as name, fp.type, fp.creator_id, fp.creation_time from face_post fp "
				+ "inner join face_user fu on fp.creator_id = fu.id and "
				+ "((fp.creator_id in (select friend_user_id from face_friend where face_user_id = ?)) or fp.creator_id = ?) and fp.type = 1 "
				+ "order by fp.creation_time DESC";
		
		return jdbcTemplate.query(query, new Object[] {id, id}, new int[] {Types.INTEGER, Types.INTEGER}, new FacePostDTORowMapper());
	}

	@Override
	public FacePostDTO edit(Long id, FacePostDTO facePostDTO) {
		FacePost facePost = facePostRepository.findById(id).orElse(null);
		facePost.setBody(facePostDTO.getBody());
		facePost.setTitle(facePostDTO.getTitle());
		return facePostDTOMapper.facePostToFacePostDTOMapper(facePostRepository.save(facePost));
	}

}
