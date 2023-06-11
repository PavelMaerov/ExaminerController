package com.example.examiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)  //404 Not Found
public class QuestionNotFoundException extends RuntimeException{
}
