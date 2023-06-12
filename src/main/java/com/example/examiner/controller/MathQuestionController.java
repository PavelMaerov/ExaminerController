package com.example.examiner.controller;

import com.example.examiner.Question;
import com.example.examiner.service.QuestionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("exam/math")
public class MathQuestionController {
    QuestionService service;

    public MathQuestionController(@Qualifier("mathQuestionService") QuestionService service) {
        this.service = service;
    }

    //exam/java/add?question=QuestionText&answer=QuestionAnswer”
    @GetMapping("add")
    Question add(@RequestParam String question, @RequestParam String answer) {
        return service.add(question, answer);
    }
    //exam/java/remove?question=QuestionText&answer=QuestionAnswer”
    @GetMapping("remove")
    Question remove(@RequestParam String question, @RequestParam String answer) {
        return service.remove(new Question(question, answer));
    }

    //exam/java”
    @GetMapping()
    Collection<Question> getAll() {
        return service.getAll();
    }
}
