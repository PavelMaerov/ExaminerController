package com.example.examiner.service;

import com.example.examiner.Question;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExaminerServiceImpl implements ExaminerService{
    //@Qualifier теперь не нужен. Все экземпляры QuestionService будут созданы и помещены в список!
    private final List<QuestionService> services;
    private final Random random = new Random();

    public ExaminerServiceImpl(List<QuestionService> services) {
        this.services = services;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        Set<Question> result = new HashSet<>();
        IntStream.range(0,amount).forEach(
                (e)->result.add(services.get(random.nextInt(2)).getRandomQuestion(result)));
        return result;
    }
}
