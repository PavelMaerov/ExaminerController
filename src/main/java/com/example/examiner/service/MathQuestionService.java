package com.example.examiner.service;

import com.example.examiner.Question;
import com.example.examiner.exception.NoMathRepositoryException;
import com.example.examiner.exception.NoQuestionsLeftException;
import com.example.examiner.exception.RepositoryIsEmptyException;
import com.example.examiner.repository.JavaQuestionRepository;
import com.example.examiner.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.*;

@Service
public class MathQuestionService implements QuestionService {

    //на попытки добавить, удалить и получить все вопросы по математике
    //должно выбрасываться исключение со статусом 405 Method Not Allowed
    @Override
    public Question add(String question, String answer) {
        throw new NoMathRepositoryException();
    }

    @Override
    public Question add(Question question) {
        throw new NoMathRepositoryException();
    }

    @Override
    public Question remove(Question question) {
        throw new NoMathRepositoryException();
    }

    @Override
    public Collection<Question> getAll() {
        throw new NoMathRepositoryException();
    }

    Random random = new Random();

    @Override
    public Question getRandomQuestion() {
        //Изобретаем вопрос на лету. Делаем вопросы калькулятора
        int firstOperand = random.nextInt(10);
        int secondOperand = random.nextInt(10);
        String[] actions = {" + "," - "," * "," / "};
        String action = actions[random.nextInt(4)];
        int result = switch(action) {
            case " + " -> firstOperand + secondOperand;
            case " - " -> firstOperand - secondOperand;
            case " * " -> firstOperand * secondOperand;
            case " / " -> firstOperand / secondOperand;
            default -> 0;
        };

        return new Question(
                "Сколько будет "+firstOperand + action + secondOperand+" ?",
                String.valueOf(result));
    }

    @Override
    public Question getRandomQuestion(Set<Question> alreadyUsed) {
        return getRandomQuestion();
    }
}
