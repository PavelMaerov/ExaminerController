package com.example.examiner.service;
import com.example.examiner.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}
