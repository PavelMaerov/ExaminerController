package com.example.examiner;

import com.example.examiner.exception.EmptyStringException;

import java.util.Objects;

public class Question implements Comparable<Question> {
    private final String question;
    private final String answer;

    public Question(String question, String answer) {
        if (question==null || answer==null || question.isEmpty() || answer.isEmpty())
            throw new EmptyStringException();
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question question1)) return false;
        return Objects.equals(question, question1.question) && Objects.equals(answer, question1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public int compareTo(Question o) {
        return (question + " " + answer).compareTo(o.getQuestion() + " " + o.getAnswer());
    }
}
