package com.example.minifaceapp.api.v1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;

import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.model.PostType;

public class FacePostDTORowMapper implements RowMapper<FacePostSearchDTO>{
	
	@Override
	public FacePostSearchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FacePostSearchDTO facePostSearchDTO = new FacePostSearchDTO();
		FaceUserDTO faceUser = new FaceUserDTO();
		PostType type = new PostType();
		
		facePostSearchDTO.setId(rs.getLong("id"));
		facePostSearchDTO.setTitle(rs.getString("title"));
		facePostSearchDTO.setBody(rs.getString("body"));
		facePostSearchDTO.setCreationTime(rs.getTimestamp("creation_time"));
		facePostSearchDTO.setLikes(rs.getInt("likes"));
		
		faceUser.setId(rs.getLong("creator_id"));
		faceUser.setName(rs.getString("name"));
		faceUser.setSurname(rs.getString("surname"));
		faceUser.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image")));
		//Byte[] imageBytes = ("image");
		
		facePostSearchDTO.setCreator(faceUser);
		
		type.setId(rs.getLong("type"));
		type.setType("user");
		facePostSearchDTO.setType(type);
		
		return facePostSearchDTO;
	}
}
