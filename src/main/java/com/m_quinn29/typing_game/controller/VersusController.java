package com.m_quinn29.typing_game.controller;

import com.m_quinn29.typing_game.model.Race;
import com.m_quinn29.typing_game.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VersusController {
    private final RaceService raceService;

    public VersusController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/versus")
    public String versus() {
        return "versus";
    }


    @GetMapping("/find-race")
    @ResponseBody
    public String findRace(@RequestParam String raceType) {
        return raceService.findRace(raceType);
    }

    @GetMapping("/next-race-prompt")
    @ResponseBody
    public String joinRace(@RequestParam String raceId) {
        Race race = raceService.getRace(raceId);
        return race.getText();
    }

}
