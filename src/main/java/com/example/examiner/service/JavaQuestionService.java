package com.example.examiner.service;

import com.example.examiner.Question;
import com.example.examiner.exception.RepositoryIsEmptyException;
import com.example.examiner.exception.NoQuestionsLeftException;
import com.example.examiner.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {
    private final QuestionRepository repository;
    private final Random random = new Random();

    public JavaQuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question add(String question, String answer) {
        return repository.add(new Question(question,answer));
    }

    @Override
    public Question add(Question question) {
        return repository.add(question);
    }

    @Override
    public Question remove(Question question) {
        return repository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return repository.getAll();
    }

    @Override
    public Question getRandomQuestion()
    {
        return getRandomQuestion(Collections.emptySet());
    }

    @Override
    public Question getRandomQuestion(Set<Question> alreadyUsed) {
        //System.out.println("Java.getRandomQuestion вход");
        Collection<Question> questions=repository.getAll();
        if (questions.isEmpty()) throw new RepositoryIsEmptyException();

        //определяем сколько вариантов доступно для выбора: количество вопросов в репозитории минус уже использованные.
        //для правильного определения этого числа alreadyUsed должно содержать строго вопросы из репозитория,
        //если там будет мусор (например вопросы из второго сервиса), то количество доступных вариантов посчитается меньше реального
        //если мусор разрешить, то нужна процедура определения его количества
        //или доверить счет мусора вызывающей процедуре, а сюда передавать его вторым параметром
        int variants = questions.size() - (alreadyUsed != null ? alreadyUsed.size():0);
        if (variants<=0) throw new NoQuestionsLeftException();

        //получаем случайный номер варианта (с 1) в пределах количества доступных вариантов
        int variant = random.nextInt(variants) + 1;

        Question currentQuestion = null;
        Iterator<Question> iterator = questions.iterator();
        for (int i = 1; i <= variant;) {  //как минимум 1 раз в цикл мы войдем (при variant=1)
            currentQuestion = iterator.next();
            if (!alreadyUsed.contains(currentQuestion)) i++;
        }
        //System.out.println("variants="+variants+" variant="+variant+" "+currentQuestion);
        //System.out.println("Java.getRandomQuestion выход");

        //можно было бы добавить сюда пополнение alreadyUsed, но он может быть иммутабельным Collections.emptySet()
        return currentQuestion;
    }
}
