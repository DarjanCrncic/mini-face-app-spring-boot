package com.example.minifaceapp.utils.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorMessageSchema {

	private String message;
	private HttpStatus status;
}
