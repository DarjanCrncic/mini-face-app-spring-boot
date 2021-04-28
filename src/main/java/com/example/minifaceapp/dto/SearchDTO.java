package com.example.minifaceapp.dto;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO {
	
	private ArrayList<String> searchParams;
	private ArrayList<String> searchWords;
	private String logicalOperand;
	
	private String wordPosition;
	private int pageNUmber;
	private int rowNumber;
}
