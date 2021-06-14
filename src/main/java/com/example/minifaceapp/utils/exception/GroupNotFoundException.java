package com.example.minifaceapp.utils.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -7575996792238103451L;
	
	private final HttpStatus status = HttpStatus.NOT_FOUND;
	private final String message = "User could not be found.";

}
