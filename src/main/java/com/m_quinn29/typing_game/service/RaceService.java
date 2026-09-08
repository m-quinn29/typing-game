package com.m_quinn29.typing_game.service;

import com.m_quinn29.typing_game.model.Player;
import com.m_quinn29.typing_game.model.Race;
import com.m_quinn29.typing_game.model.RaceProgressMessage;
import com.m_quinn29.typing_game.model.RaceState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RaceService {

    private final Map<String, Race> races = new HashMap<>();

    public String createRace() {
        String raceId = UUID.randomUUID().toString();
        races.put(raceId, new Race(raceId));
        return raceId;
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
