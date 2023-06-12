package com.example.examiner.service;

import com.example.examiner.Question;
import com.example.examiner.exception.NoQuestionsLeftException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {
    @Mock
    QuestionService javaQuestionService;
    @Mock
    QuestionService mathQuestionService;
    @Mock
    Random random;

    //тестируемый объект. Правильное имя out. @InjectMocks не делаем. Инъекцию делаем через конструктор
    ExaminerServiceImpl examinerServiceImpl;

    //@Mock
    List<Question> testJavaQuestions = of(
            new Question("JavaQuestion1","JavaAnswer1"),
            new Question("JavaQuestion2","JavaAnswer2"),
            new Question("JavaQuestion3","JavaAnswer3"));
    List<Question> testMathQuestions = of(
            new Question("MathQuestion1","MathAnswer1"),
            new Question("MathQuestion2","MathAnswer2"),
            new Question("MathQuestion3","MathAnswer3"));
    @BeforeEach
    void Init() {
        //сервисы могут быть созданы только в @BeforeEach. До этого они = null и List.of их не примет
        List<QuestionService> services = of(javaQuestionService, mathQuestionService); //0-java, 1-math
        examinerServiceImpl = new ExaminerServiceImpl(services); //чтобы не было непокрытых строк, вызовем конструктор, который нам сейчас не нужен
        examinerServiceImpl = new ExaminerServiceImpl(services, random); //а нужен этот, где производим инжекцию самостоятельно

        //делаем заглушки для getRandomQuestion сервисов
        when(javaQuestionService.getRandomQuestion(any()))
                .thenReturn(testJavaQuestions.get(0),  //получаем последовательно вопросы 1, 2, 3 по jave
                            testJavaQuestions.get(1),
                            testJavaQuestions.get(2))
                .thenThrow(new NoQuestionsLeftException());  //будет и для всех последующих вызовов getRandomQuestion
        when(mathQuestionService.getRandomQuestion(any()))
                .thenReturn(testMathQuestions.get(0), //получаем последовательно вопросы 1, 2, 3 по math
                            testMathQuestions.get(1),
                            testMathQuestions.get(2))
                .thenThrow(new NoQuestionsLeftException());
        when(random.nextInt(anyInt())).thenReturn(0,1,0,1,0,1,0,1,0,1,0,1); //пусть сервисы чередуются
    }
    @Test
    void getQuestionsTest() {
        List<Question> expected = of( //список уже отсортирован по вопросу
                testJavaQuestions.get(0),
                testJavaQuestions.get(1),
                testJavaQuestions.get(2),
                testMathQuestions.get(0),
                testMathQuestions.get(1));

        Collection<Question> result = examinerServiceImpl.getQuestions(5);
        List<Question> actual = new ArrayList<>();
        actual.addAll(result);
        Collections.sort(actual);
        //альтернативный вариант actual = result.stream().sorted().toList()

        assertEquals(expected, actual);
        assertEquals(5,result.size());
        assertThrows(NoQuestionsLeftException.class, ()->examinerServiceImpl.getQuestions(7));
    }

}
