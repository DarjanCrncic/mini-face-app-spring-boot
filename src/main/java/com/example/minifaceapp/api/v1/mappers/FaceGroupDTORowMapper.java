package com.example.minifaceapp.api.v1.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.minifaceapp.api.v1.dtos.FaceGroupSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;

public class FaceGroupDTORowMapper implements RowMapper<FaceGroupSearchDTO>{

	@Override
	public FaceGroupSearchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FaceGroupSearchDTO faceGroupSearchDTO = new FaceGroupSearchDTO();
		FaceUserDTO faceUserDTO = new FaceUserDTO();
		
		faceGroupSearchDTO.setDescription(rs.getString("description"));
		faceGroupSearchDTO.setId(rs.getLong("id"));
		faceGroupSearchDTO.setName(rs.getString("name"));
		
		faceUserDTO.setId(rs.getLong("owner_id"));
		faceUserDTO.setName(rs.getString("owner_name"));
		faceUserDTO.setSurname(rs.getString("owner_surname"));

		faceGroupSearchDTO.setOwner(faceUserDTO);
		return faceGroupSearchDTO;
	}

}
