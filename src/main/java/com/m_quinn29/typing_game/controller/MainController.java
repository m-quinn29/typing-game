package com.m_quinn29.typing_game.controller;

import com.m_quinn29.typing_game.model.TextPrompt;
import com.m_quinn29.typing_game.service.MainEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {

    private final MainEnvironment mainEnvironment;

    public MainController(MainEnvironment mainEnvironment){
        this.mainEnvironment = mainEnvironment;
    }

    @GetMapping("/")
    public String index(Model model) {

        TextPrompt nextTextPrompt = mainEnvironment.getMainService().getRandomPrompt();
        String promptText = nextTextPrompt.getText();

        model.addAttribute("promptVariable", promptText);

        return "index";
    }
}
