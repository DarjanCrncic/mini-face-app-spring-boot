package com.example.minifaceapp.api.v1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;

public class FacePostDTORowMapper implements RowMapper<FacePostSearchDTO>{
	
	@Override
	public FacePostSearchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FacePostSearchDTO facePostSearchDTO = new FacePostSearchDTO();
		
		facePostSearchDTO.setId(rs.getLong("id"));
		facePostSearchDTO.setTitle(rs.getString("title"));
		facePostSearchDTO.setCreatorName(rs.getString("name"));
		facePostSearchDTO.setBody(rs.getString("body"));
		facePostSearchDTO.setCreationTime(rs.getDate("creation_time"));
		facePostSearchDTO.setCreatorId(rs.getLong("creator_id"));
		facePostSearchDTO.setType(rs.getLong("type"));
		facePostSearchDTO.setLikes(rs.getInt("likes"));
		
		return facePostSearchDTO;
	}
}
