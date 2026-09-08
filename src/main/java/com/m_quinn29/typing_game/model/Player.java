package com.m_quinn29.typing_game.model;

public class Player {
    private final String playerId;
    private int charactersTyped;
    private int wpm;
    private boolean finished;

    public Player(String playerId) {
        this.playerId = playerId;
        this.charactersTyped = 0;
        this.wpm = 0;
        this.finished = false;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getCharactersTyped() {
        return charactersTyped;
    }

    public void setCharactersTyped(int charactersTyped) {
        this.charactersTyped = charactersTyped;
    }

    public int getWpm() {
        return wpm;
    }

    public void setWpm(int wpm) {
        this.wpm = wpm;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
