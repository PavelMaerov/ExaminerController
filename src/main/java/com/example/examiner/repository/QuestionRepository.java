package com.example.examiner.repository;

import com.example.examiner.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question add(Question question);
    Question remove(Question question);
    Collection<Question> getAll();
}
