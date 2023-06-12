package com.example.examiner.service;

import com.example.examiner.Question;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface QuestionService {
    Question add(String question, String answer);
    Question add(Question question);
    Question remove(Question question);
    Collection<Question> getAll();
    Question getRandomQuestion();
    //возвращает случайный вопрос, не включенный в Set
    Question getRandomQuestion(Set<Question> alreadyUsed);
}
