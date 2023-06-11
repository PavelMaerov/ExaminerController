package com.example.examiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  //405
public class NoMathRepositoryException extends RuntimeException{
}
