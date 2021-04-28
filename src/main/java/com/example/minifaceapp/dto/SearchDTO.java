package com.example.minifaceapp.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class SearchDTO {
	
	private ArrayList<String> searchParams;
	private ArrayList<String> searchWords;
	private String logicalOperand;
	
	private String wordPosition;
	private int pageNUmber;
	private int rowNumber;
}
