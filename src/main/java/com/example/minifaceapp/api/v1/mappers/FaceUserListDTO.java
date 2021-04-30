package com.example.minifaceapp.api.v1.mappers;

import java.util.List;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceUserListDTO {
	
	List<FaceUserDTO> faceUsers;
}
