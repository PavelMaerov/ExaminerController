package com.example.examiner.controller;

import com.example.examiner.Question;
import com.example.examiner.service.ExaminerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ExamController {
    ExaminerService service;

    public ExamController(ExaminerService service) {
        this.service = service;
    }

    @GetMapping("exam/{amountQuestions}")
    Collection<Question> getQuestions(@PathVariable int amountQuestions) {
        return service.getQuestions(amountQuestions);
    }
}
