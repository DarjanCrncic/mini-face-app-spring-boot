package com.example.minifaceapp.utils.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public final ResponseEntity<Object> handleAllUserExceptions(UserNotFoundException ex) {
    return new ResponseEntity<>(new ErrorMessageSchema(ex.getMessage(), ex.getStatus()), ex.getStatus());
  }
  
  @ExceptionHandler(GroupNotFoundException.class)
  public final ResponseEntity<Object> handleAllGroupExceptions(UserNotFoundException ex) {
    return new ResponseEntity<>(new ErrorMessageSchema(ex.getMessage(), ex.getStatus()), ex.getStatus());
  }
}