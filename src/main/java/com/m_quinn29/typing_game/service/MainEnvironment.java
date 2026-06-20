package com.m_quinn29.typing_game.service;

import com.m_quinn29.typing_game.repository.TextPromptRepository;
import org.springframework.stereotype.Component;

@Component
public class MainEnvironment {

    private final MainService mainService;

    public MainEnvironment(MainService mainService){
        this.mainService = mainService;

    }

    public MainService getMainService(){
        return mainService;
    }
}
