package com.example.minifaceapp.api.v1.dtos;

import java.util.ArrayList;

import lombok.Data;

@Data
public class SearchDTO {
	
	private ArrayList<String> searchParams;
	private ArrayList<String> searchWords;
	private String logicalOperand;
	
	private String wordPosition;
	private int pageNumber;
	private int rowNumber;
}
