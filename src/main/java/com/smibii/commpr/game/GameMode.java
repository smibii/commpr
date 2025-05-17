package com.smibii.commpr.game;

public class GameMode {
    private final String name;
    public final boolean elimination;
    public final boolean canJoinMidGame;
    public final boolean canSpectate;
    public final boolean canFreecam;
    public final int lives;
    public final int maxPlayers;
    public final int minPlayers;
    public final int timeLimit;

    public GameMode(
            String name,
            boolean elimination,
            boolean canJoinMidGame,
            boolean canSpectate,
            boolean canFreecam,
            int lives,
            int maxPlayers,
            int minPlayers,
            int timeLimit
    ) {
        this.name = name;
        this.elimination = elimination;
        this.canJoinMidGame = canJoinMidGame;
        this.canSpectate = canSpectate;
        this.canFreecam = canFreecam;
        this.lives = lives;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.timeLimit = timeLimit;
    }

    public String getName() {
        return name;
    }
}
