package com.m_quinn29.typing_game.service;

import com.m_quinn29.typing_game.model.TextPrompt;
import com.m_quinn29.typing_game.repository.TextPromptRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class MainService {

    private final TextPromptRepository textPromptRepository;

    public MainService(TextPromptRepository textPromptRepository) {
        this.textPromptRepository = textPromptRepository;
    }

    public TextPrompt getRandomPrompt(){
        int textPromptRepositorySize = (int) textPromptRepository.count();

        if (textPromptRepositorySize == 0){
            return null;
        }

        return textPromptRepository.findById(ThreadLocalRandom.current().nextInt(1, textPromptRepositorySize+1));
    }
}
