package com.example.Quora.services;

import com.example.Quora.models.Answer;
import com.example.Quora.repositories.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer) {
        Answer ans = Answer.builder()
                .question(answer.getQuestion())
                .text(answer.getText())
                .user(answer.getUser())
                .build();
        return answerRepository.save(ans);
    }

    public Optional<Answer> findAnswerById(UUID answerId) {
        return answerRepository.findById(answerId);
    }

    public Answer updateAnswer(Answer oldAnswer, Answer newAnswer) {
        if(newAnswer.getText() != null) {
            oldAnswer.setText(newAnswer.getText());
            answerRepository.save(oldAnswer);
        }
        return oldAnswer;
    }
}
