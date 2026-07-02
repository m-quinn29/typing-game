package com.m_quinn29.typing_game.service;

import com.m_quinn29.typing_game.model.RandomWord;
import com.m_quinn29.typing_game.model.TextPrompt;
import com.m_quinn29.typing_game.repository.RandomWordRepository;
import com.m_quinn29.typing_game.repository.TextPromptRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class MainService {

    private final TextPromptRepository textPromptRepository;
    private final RandomWordRepository randomWordRepository;

    public MainService(TextPromptRepository textPromptRepository, RandomWordRepository randomWordRepository) {
        this.textPromptRepository = textPromptRepository;
        this.randomWordRepository = randomWordRepository;
    }

    public TextPrompt getRandomPrompt(){
        int textPromptRepositorySize = (int) textPromptRepository.count();

        if (textPromptRepositorySize == 0){
            return null;
        }

        return textPromptRepository.findById(ThreadLocalRandom.current().nextInt(1, textPromptRepositorySize+1));
    }

    public String getNextRandomWordSet(){
        StringBuilder randomWordString = new StringBuilder(150);
        int randomWordRepositorySize = (int) randomWordRepository.count();

        for (int i = 0 ; i < 100 ; i++){
            RandomWord randomWord = randomWordRepository.findById(ThreadLocalRandom.current().nextInt(1, randomWordRepositorySize+1));
            randomWordString.append(randomWord.getWord()).append(" ");
        }

        return randomWordString.toString().stripTrailing();
    }
}
