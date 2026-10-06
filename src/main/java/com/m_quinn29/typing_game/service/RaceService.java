package com.m_quinn29.typing_game.service;

import com.m_quinn29.typing_game.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RaceService {

    private final Map<String, Race> races = new HashMap<>();
    private final Map<String, String> openRaces = new HashMap<>();

    private final MainService mainService;

    public RaceService (MainService mainService) {
        this.mainService = mainService;

        openRaces.put("quote", null);
        openRaces.put("word", null);
    }

    public String createRace(String raceType) {
        String raceId = UUID.randomUUID().toString();
        String prompt;

        if (raceType.equals("quote")) {
            prompt = mainService.getRandomPrompt().getText();
        }
        else if (raceType.equals("word")){
            prompt = mainService.getNextRandomWordSet();
        } else {
            throw new IllegalArgumentException("Invalid race type: " + raceType);
        }

        // TODO: Creates a race with the prompt text but should also send back TextPrompt's author, title etc
        races.put(raceId, new Race(raceId, prompt));
        return raceId;
    }

    public String findRace(String raceType) {
        String openRace = openRaces.get(raceType);
        if (openRace == null) {
            String raceId = createRace(raceType);
            openRaces.put(raceType, raceId);
            return raceId;
        }

        return openRace;
    }

    public Race getRace(String raceId) {
        return races.get(raceId);
    }

    public RaceState updateRaceProgress(String raceId, RaceProgressMessage message) {
         String playerId = message.getPlayerId();

         Race race = races.get(raceId);
         Player player = race.getPlayer(playerId);
         race.removePlayer(playerId);

         player.setFinished(message.isFinished());
         player.setWpm(message.getWpm());
         player.setCharactersTyped(message.getCharactersTyped());

         race.addPlayer(player);

         return new RaceState(race.isStarted(), race.isFinished(), race.getPlayers());
    }

}
