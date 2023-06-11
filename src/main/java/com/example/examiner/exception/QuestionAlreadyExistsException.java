package com.example.examiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)  //208. Пусть не 400, пусть клиент считает, что все ок
public class QuestionAlreadyExistsException extends RuntimeException{
}
