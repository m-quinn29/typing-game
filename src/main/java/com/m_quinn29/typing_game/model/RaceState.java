package com.m_quinn29.typing_game.model;

import java.util.List;
import java.util.Map;

/**
 * A representation of a race state to be sent as a SimpMessage
 */
public class RaceState {
    private final boolean started;
    private final boolean finished;

    Map<String, Player> players;

    public RaceState(boolean started, boolean finished, Map<String, Player> players) {
        this.started = started;
        this.finished = finished;
        this.players = players;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isStarted() {
        return started;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }
}
