package com.m_quinn29.typing_game.model;


/**
 * A representation of a race progress message sent from player to the server.
 */
public class RaceProgressMessage {
    private String playerId;
    private int charactersTyped;
    private int wpm;
    private boolean finished;

    public RaceProgressMessage() {

    }

    public RaceProgressMessage(String playerId, int charactersTyped, int wpm, boolean finished) {
        this.playerId = playerId;
        this.charactersTyped = charactersTyped;
        this.wpm = wpm;
        this.finished = finished;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getCharactersTyped() {
        return charactersTyped;
    }

    public int getWpm() {
        return wpm;
    }

    public boolean isFinished() {
        return finished;
    }
}
