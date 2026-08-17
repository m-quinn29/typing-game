package com.m_quinn29.typing_game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VersusController {

    @GetMapping("/versus")
    public String versus() {
        return "versus";
    }

}
