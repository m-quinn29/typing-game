package com.m_quinn29.typing_game.model;


import java.util.List;
import java.util.Map;


public class Race {
    private String raceId;
    private String text;

    private boolean started;
    private boolean finished;

    private Map<String, Player> players;

    public Race() {

    }

    public Race(String raceId) {
        this.raceId = raceId;
    }

    public String getId() {
        return raceId;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String playerId) {
        return players.get(playerId);
    }

    public void removePlayer(String playerId) {
        players.remove(playerId);
    }

    public void addPlayer(Player player) {
        this.players.put(player.getPlayerId(), player);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
