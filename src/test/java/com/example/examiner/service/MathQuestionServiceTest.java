package com.example.examiner.service;

import com.example.examiner.Question;
import com.example.examiner.exception.NoMathRepositoryException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MathQuestionServiceTest {
    //осталась непокрытой строка switch case - default -> 0;
    //как ее покрыть не знаю, ее бы удалить, но без нее выражение не компилируется

    //репозитория здесь нет
    MathQuestionService out = new MathQuestionService();

    @Test
    void addByString() {
        assertThrows(NoMathRepositoryException.class, () -> out.add("", ""));
    }

    @Test
    void addByQuestion() {
        assertThrows(NoMathRepositoryException.class, () -> out.add(new Question("q","a")));
    }

    @Test
    void remove() {
        assertThrows(NoMathRepositoryException.class, () -> out.remove(new Question("q","a")));
    }

    @Test
    void getAll() {
        assertThrows(NoMathRepositoryException.class, () -> out.getAll());
    }

    void checkQuestion(Question question) {
        //Сколько будет "+firstOperand + action + secondOperand+" ?"
        assertTrue(question.getQuestion().matches(
                "Сколько будет \\d [+\\-*/] \\d \\?"));
        assertTrue(question.getAnswer().matches(
                "-{0,1}\\d{1,2}"));
    }
    @Test
    void getRandomQuestion() {
        //делаем 100 раз, чтобы покрыть все строки case c разными действиями.
        //мокать random не стал. Мне хватило его мока в ExaminerServiceImplTest
        for (int i = 0; i < 100 ; i++) checkQuestion(out.getRandomQuestion());
    }

    @Test
    void getRandomQuestionExceptUsed() {
        //в этом сервисе список использованных вопросов игнорируется при генерации нового.
        //Теоретически может и совпасть. Но я это не проверял и несколько попыток генерации не делал
        checkQuestion(out.getRandomQuestion(Set.of(new Question("q","a"))));
    }
}