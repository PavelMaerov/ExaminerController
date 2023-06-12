package com.example.examiner.service;

import com.example.examiner.Question;
import com.example.examiner.exception.NoQuestionsLeftException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    //@Qualifier теперь не нужен. Все экземпляры QuestionService будут созданы и помещены в список!
    private final List<QuestionService> services;
    private final Random random;
    @Autowired
    public ExaminerServiceImpl(List<QuestionService> services) {
        this.services = services;
        random = new Random();
    }

    //конструктор для отладки с random, где установлен seed
    public ExaminerServiceImpl(List<QuestionService> services, Random random) {
        this.services = services;
        this.random = random;
    }

    //немного изменил задачу "создать коллекцию и заполнить её с помощью вызова getRandomQuestion
    //у QuestionService случайными вопросами."
    //Ведь если вопросов много и они почти исчерпаны,
    //то, выбирая случайно, мы можем очень долго ждать неиспользованный из исходного набора
    //Поэтому усложнил задание и включил в интерфейс и реализовал дополнительный метод -
    //прошу QuestionService вернуть случайный вопрос, кроме тех, что входят в список использованных
    //здесь буду пользоваться именно им, т.е. с сигнатурой отличной от задания
    @Override
    public Collection<Question> getQuestions(int amountQuestions) {
        Set<Question> result = new HashSet<>();
        //В usedQuestions лежат сервисы и уже использованные вопросы из них.
        //Можно возвратить не result, а объединение этих подмножеств.
        //Но ведь это объединение все равно надо будет делать и занимать памать,
        //поэтому пополнение result по мере получения вопросов, лишнего не займет.
        Map<QuestionService, Set<Question>> usedQuestions = services.stream().collect(
                Collectors.toMap(e -> e, e->new HashSet<>())); //

        //IntStream.range(0, amountQuestions).forEach(i -> {
        int i = 0;
        for (; i < amountQuestions; i++) {
            //5 попыток получить следующий вопрос, даже если в каком-то сервисе они исчерпаны
            //или, чтобы не считать попытки,
            //надо где-то отдельно хранить сервис математических вопросов, который бесконечный (в нашем случае)
            //или хранить для сервисов флаг исчерпанности, чтобы не стрелять по сервисам вхолостую
            int attemptCount = 5;
            while (attemptCount > 0) {
                try {
                    int randomServiceNumber = random.nextInt(services.size());
                    QuestionService randomService = services.get(randomServiceNumber);
                    //получаем из случайного сервиса случайный вопрос. В параметре - использованные
                    Question randomQuestion = randomService.getRandomQuestion(usedQuestions.get(randomService));
                    //добавляем в возвращаемый результат
                    result.add(randomQuestion);
                    //добавляем в список использованных для сервиса
                    usedQuestions.get(randomService).add(randomQuestion);
                    break;
                } catch (NoQuestionsLeftException e) {
                    //если в сервисе свободных вопросов нет,
                    //уменьшим количество попыток и попробуем другой случайный сервис
                    attemptCount--;
                }
            }
            //если вышли из цикла из-за нехватки попыток, то бросаем исключение
            if (attemptCount == 0) throw(new NoQuestionsLeftException());
        }
        return result;
        }
    }
