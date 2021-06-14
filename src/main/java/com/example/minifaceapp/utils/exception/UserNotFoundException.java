package com.example.minifaceapp.utils.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -7305813945482066539L;
	
	private final HttpStatus status = HttpStatus.NOT_FOUND;
	private final String message = "User could not be found.";
	
}
