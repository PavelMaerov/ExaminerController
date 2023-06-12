package com.example.examiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)  //429 Too Many Requests  //по заданию 400 BAD_REQUEST
public class NoQuestionsLeftException extends RuntimeException{
}
