package com.example.examiner.service;

import com.example.examiner.Question;
import com.example.examiner.exception.NoMathRepositoryException;
import org.springframework.stereotype.Service;

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
        //System.out.println("Math.getRandomQuestion вход");
        //Изобретаем вопрос на лету. Делаем вопросы калькулятора
        int firstOperand = random.nextInt(10); //одна цифра, чтобы было удобно тестировать
        int secondOperand = random.nextInt(9)+1; //+1 чтобы не было деления на 0
        String[] actions = {" + "," - "," * "," / "};
        String action = actions[random.nextInt(4)];

        int result = switch(action) {
            case " + " -> firstOperand + secondOperand;
            case " - " -> firstOperand - secondOperand;
            case " * " -> firstOperand * secondOperand;
            case " / " -> firstOperand / secondOperand;
            default -> 0;
        };
        //System.out.println("Math.getRandomQuestion выход");
        return new Question(
                "Сколько будет "+firstOperand + action + secondOperand+" ?",
                String.valueOf(result));
    }

    @Override
    public Question getRandomQuestion(Set<Question> alreadyUsed) {
        //в этом сервисе список использованных вопросов игнорируется при генерации нового.
        //Теоретически может и совпасть. Но я это не проверял и несколько попыток генерации не делал
        return getRandomQuestion();
    }
}
