package com.example.minifaceapp.mappers;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.minifaceapp.dto.FaceUserDTO;

public class FaceUserDTOMapper implements RowMapper<FaceUserDTO>{

	@Override
	public FaceUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FaceUserDTO faceUserDTO = new FaceUserDTO();
		
		faceUserDTO.setAge(rs.getInt("age"));
		faceUserDTO.setCity(rs.getString("city"));
		faceUserDTO.setCountry(rs.getString("country"));
		faceUserDTO.setEmail(rs.getString("email"));
		faceUserDTO.setGender(rs.getString("gender"));
		faceUserDTO.setId(rs.getLong("id"));
		faceUserDTO.setName(rs.getString("name"));
		faceUserDTO.setSurname(rs.getString("surname"));
		faceUserDTO.setUsername(rs.getString("username"));
		return faceUserDTO;
	}

}
