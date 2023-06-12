package com.example.examiner.repository;

import com.example.examiner.Question;
import com.example.examiner.exception.QuestionAlreadyExistsException;
import com.example.examiner.exception.QuestionNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionRepositoryTest {
    JavaQuestionRepository out = new JavaQuestionRepository();

    @Test
    void add() {
        Question question = new Question("Вопрос", "Ответ");
        assertEquals(question, out.add(question));
        assertTrue(out.getAll().contains(question));
        //негативные сценарии
        assertThrows(QuestionAlreadyExistsException.class, () -> out.add(question));
    }

    @Test
    void remove() {
        Question question = new Question("Вопрос", "Ответ");
        out.add(question);
        assertEquals(question, out.remove(question));
        assertFalse(out.getAll().contains(question));
        //негативные сценарии
        assertThrows(QuestionNotFoundException.class, () -> out.remove(question));
    }

    @Test
    void getAll() {
        List<Question> expected = List.of(
                new Question("Вопрос1", "Ответ1"),
                new Question("Вопрос2", "Ответ2"),
                new Question("Вопрос3", "Ответ3"));
        out.add(expected.get(0));
        out.add(expected.get(1));
        out.add(expected.get(2));
        assertEquals(expected, out.getAll().stream().sorted().toList());
        //негативных сценариев нет
    }
}