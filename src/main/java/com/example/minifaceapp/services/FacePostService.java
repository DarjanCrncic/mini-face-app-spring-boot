package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;
import com.example.minifaceapp.api.v1.dtos.ReportDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;

public interface FacePostService extends CrudService<FacePostDTO, Long> {

	List<FacePostSearchDTO> searchVissiblePosts(SearchDTO searchDTO, Long id);

	FacePostDTO edit(Long id, FacePostDTO facePostDTO);

	FacePostDTO saveGroup(FacePostDTO facePostDTO, Long groupId);

	void deleteGroupPostById(Long groupId, Long id);

	String generateReportQuery(ReportDTO reportDTO);

}
