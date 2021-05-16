package com.example.minifaceapp.api.v1.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ReportDTO {
	
	List<String> commentNumbers;
	List<String> commentOperations;
	List<String> likeNumbers;
	List<String> likeOperations;
	String startDate;
	String endDate;
	String titleKeyword;
}
