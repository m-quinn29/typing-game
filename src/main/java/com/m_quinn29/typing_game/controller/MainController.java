package com.m_quinn29.typing_game.controller;

import com.m_quinn29.typing_game.model.TextPrompt;
import com.m_quinn29.typing_game.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService){
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("promptVariable", nextPrompt("quote"));
        return "index";
    }

    @GetMapping("/next-prompt")
    @ResponseBody
    public String nextPrompt(@RequestParam(name = "gameMode", defaultValue = "quote") String mode){
        String returnString = "";
        if (mode.equals("quote")){
            TextPrompt nextTextPrompt = mainService.getRandomPrompt();
            returnString = nextTextPrompt.getText();
        }
        else if (mode.equals("word")){
            returnString = mainService.getNextRandomWordSet();
        }
        return returnString;
    }
}
