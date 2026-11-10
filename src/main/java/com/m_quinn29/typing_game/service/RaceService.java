package com.m_quinn29.typing_game.service;

import com.m_quinn29.typing_game.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RaceService {

    private final Map<String, Race> races = new HashMap<>();
    private final Map<String, String> openRaces = new HashMap<>();

    private final Random random = new Random();

    private final MainService mainService;

    public RaceService (MainService mainService) {
        this.mainService = mainService;

        openRaces.put("quote", null);
        openRaces.put("word", null);
    }

    public String createRace(String raceType) {
        // 36 choose N chance of collision
        String raceId = generateRandomId(6);
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

    public MatchmakingResponse findRace(String raceType) {
        String openRace = openRaces.get(raceType);
        if (openRace == null) {
            String raceId = createRace(raceType);
            openRaces.put(raceType, raceId);
            openRace = raceId;
        }

        String playerId = generateRandomId(10);

        races.get(openRace).addPlayer(new Player(playerId));
        return new MatchmakingResponse(openRace, playerId);
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

    private String generateRandomId(int length) {
        StringBuilder builder = new StringBuilder();

        String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int charPoolLength = charPool.length();

        for (int i = 0; i < length; i++) {
            int randInt = random.nextInt(charPoolLength);
            char randChar = charPool.charAt(randInt);
            builder.append(randChar);
        }

        return builder.toString();
    }

}
