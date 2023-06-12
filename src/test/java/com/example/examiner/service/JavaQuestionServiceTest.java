package com.example.examiner.service;

import com.example.examiner.Question;
import com.example.examiner.exception.EmptyStringException;
import com.example.examiner.exception.QuestionAlreadyExistsException;
import com.example.examiner.exception.QuestionNotFoundException;
import com.example.examiner.exception.RepositoryIsEmptyException;
import com.example.examiner.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {
    @Mock
    QuestionRepository questionRepository;
    @InjectMocks
    JavaQuestionService out;

    @Test
    void addByString() {
        Question question = new Question("Вопрос", "Ответ");
        when(questionRepository.add(eq(question))).thenReturn(question);
        assertEquals(question, out.add("Вопрос", "Ответ"));
        when(questionRepository.add(eq(question))).thenThrow(QuestionAlreadyExistsException.class);
        //негативные сценарии
        assertThrows(QuestionAlreadyExistsException.class, () -> out.add("Вопрос", "Ответ"));
        assertThrows(EmptyStringException.class, () -> out.add("", "Ответ"));
        assertThrows(EmptyStringException.class, () -> out.add("Вопрос", ""));
    }

    @Test
    void addByQuestion() {
        Question question = new Question("Вопрос", "Ответ");
        when(questionRepository.add(eq(question))).thenReturn(question);
        assertEquals(question, out.add(question));
        //негативные сценарии
        when(questionRepository.add(eq(question))).thenThrow(QuestionAlreadyExistsException.class);
        assertThrows(QuestionAlreadyExistsException.class, () -> out.add(question));
    }

    @Test
    void remove() {
        //тестируем тело метода -  return repository.remove(question).
        //Проверяем, что туда не написали ничего лишнего
        Question question = new Question("Вопрос", "Ответ");
        when(questionRepository.remove(eq(question))).thenReturn(question);
        assertEquals(question, out.remove(question));
        //негативные сценарии
        when(questionRepository.remove(eq(question))).thenThrow(QuestionNotFoundException.class);
        assertThrows(QuestionNotFoundException.class, () -> out.remove(question));
    }

    @Test
    void getAll() {
        Collection<Question> expected = List.of(
                new Question("Вопрос1", "Ответ1"),
                new Question("Вопрос2", "Ответ2"));
        when(questionRepository.getAll()).thenReturn(expected);
        assertEquals(expected, out.getAll());
        when(questionRepository.getAll()).thenReturn(Collections.emptySet());
        assertEquals(Collections.emptySet(), out.getAll());
        //негативных сценариев нет
    }

    @Test
    void getRandomQuestion() {
        Collection<Question> getAllList = List.of(  //делаем List, чтобы был строгий порядок. Это нужно для сравнения
                new Question("Вопрос1", "Ответ1"),
                new Question("Вопрос2", "Ответ2"),
                new Question("Вопрос3", "Ответ3"));
        when(questionRepository.getAll()).thenReturn(getAllList);
        assertTrue(getAllList.contains(out.getRandomQuestion()));

        Set<Question> result = new HashSet<>();
        //В множество result 20 раз добавим разные случайные вопросы
        for (int i = 0; i < 20; i++) result.add(out.getRandomQuestion());
        //убедимся, что получили исходный набор. Т.е. что хотя бы 1 раз каждый вопрос был выбран
        //хотя существует небольшая вероятность, что какой-то вопрос не был ни разу выбран.
        //Это тоже интересно. Значит плохо работает генератор случайных чисел
        List<Question> actual = result.stream().sorted().toList(); //превращаем Set в List для сортировки
        assertEquals(getAllList, actual);

        //негативные сценарии
        when(questionRepository.getAll()).thenReturn(Collections.emptySet());
        assertThrows(RepositoryIsEmptyException.class, () -> out.getRandomQuestion());
    }

    @Test
    void getRandomQuestionExceptUsed() {
        Collection<Question> getAllList = List.of( //все вопросы репозитория
                new Question("Вопрос1", "Ответ1"),
                new Question("Вопрос2", "Ответ2"),
                new Question("Вопрос3", "Ответ3"),
                new Question("Вопрос4", "Ответ4"),
                new Question("Вопрос5", "Ответ5"));
        Set<Question> usedQuestions = Set.of(  //пусть 2 и 4 - уже использованы
                new Question("Вопрос2", "Ответ2"),
                new Question("Вопрос4", "Ответ4"));

        when(questionRepository.getAll()).thenReturn(getAllList);
        assertTrue(getAllList.contains(out.getRandomQuestion(usedQuestions))); //проверим получение единичного вопроса

        //проверим получение всех вопросов минус использованные
        Set<Question> result = new HashSet<>();
        //В множество result 20 раз добавим разные случайные вопросы
        for (int i = 0; i < 20; i++) result.add(out.getRandomQuestion(usedQuestions));
        List<Question> actual = result.stream().sorted().toList(); //сортируем полученное от getRandomQuestion
        //делаем getAllList мутабельным
        List<Question> expected = new ArrayList<>();
        expected.addAll(getAllList);
        expected.removeAll(usedQuestions);
        //убедимся, что получили исходный набор минус вопросы 2 и 4.
        assertEquals(expected, actual);

        //негативные сценарии
        when(questionRepository.getAll()).thenReturn(Collections.emptySet());
        assertThrows(RepositoryIsEmptyException.class, () -> out.getRandomQuestion(usedQuestions));
    }
}