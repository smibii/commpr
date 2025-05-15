package com.smibii.commpr.game;

import com.smibii.commpr.enums.GameModeTypes;

public class GameSettings {
    public GameModeTypes type;
    public boolean elimination;
    public boolean canJoinMidGame;
    public boolean canSpectate;
    public boolean canFreeCam;
    public boolean randomSpawn;
    public int lives;
    public int maxPlayers;
    public int minPlayers;
    public int playerCount;
    public int timeLimit;

    public SpawnPointManager spawnPointManager = new SpawnPointManager();

    public GameSettings(
        GameModeTypes type,
        boolean elimination,
        boolean canJoinMidGame,
        boolean canSpectate,
        boolean canFreeCam,
        boolean randomSpawn,
        int lives,
        int maxPlayers,
        int minPlayers,
        int playerCount,
        int timeLimit
    ) {
        this.type = type;
        this.elimination = elimination;
        this.canJoinMidGame = canJoinMidGame;
        this.canSpectate = canSpectate;
        this.canFreeCam = canFreeCam;
        this.randomSpawn = randomSpawn;
        this.lives = lives;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.playerCount = playerCount;
        this.timeLimit = timeLimit;
    }
}
