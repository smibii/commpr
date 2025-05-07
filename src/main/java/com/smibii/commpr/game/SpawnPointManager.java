package com.smibii.commpr.game;

import com.smibii.commpr.enums.GameModeTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Load and register spawnpoints
// Save spawnpoints to file
// spawnpoints.json
// void teleportTo(spawnPointId)
// void teleportToRandom()
public class SpawnPointManager {
    public Map<GameModeTypes, ArrayList<SpawnPoint>> spawnPointList = new HashMap<>();

    public SpawnPointManager() {
        GameModeTypes.forEach(this::registerGameType);
    }

    private void registerGameType(GameModeTypes type) {
        spawnPointList.put(type, new ArrayList<SpawnPoint>());
    }
}