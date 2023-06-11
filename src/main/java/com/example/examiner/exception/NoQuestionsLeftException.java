package com.example.examiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)  //429 Too Many Requests  //по заданию BAD_REQUEST
public class NoQuestionsLeftException extends RuntimeException{
}
