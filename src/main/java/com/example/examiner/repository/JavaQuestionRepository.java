package com.example.examiner.repository;

import com.example.examiner.Question;
import com.example.examiner.exception.QuestionAlreadyExistsException;
import com.example.examiner.exception.QuestionNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
@Repository
public class JavaQuestionRepository implements QuestionRepository {
    private final Set<Question> questions = new HashSet<>();
    @Override
    public Question add(Question question) {
        if (!questions.add(question)) throw new QuestionAlreadyExistsException();
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) throw new QuestionNotFoundException();
        return question;
     }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet(questions);
    }
}
