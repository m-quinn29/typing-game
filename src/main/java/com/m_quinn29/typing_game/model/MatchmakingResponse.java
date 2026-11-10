package com.m_quinn29.typing_game.model;

/**
 * Record to act as a tuple when joining a race
 * @param raceId of race/lobby
 * @param playerId temporary id of player for the specific race only
 */
public record MatchmakingResponse(String raceId, String playerId) {}
