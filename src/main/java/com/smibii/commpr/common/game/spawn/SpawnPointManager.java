package com.smibii.commpr.common.game.spawn;

import com.smibii.commpr.common.game.modes.GameModeTypes;
import com.smibii.commpr.common.game.spawn.SpawnPoint;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Load and register spawnpoints
// Save spawnpoints to file
// spawnpoints.json
public class SpawnPointManager {
    public Map<GameModeTypes, ArrayList<SpawnPoint>> spawnPointMap = new HashMap<>();

    public SpawnPointManager() {
        GameModeTypes.forEach(this::registerGameType);
    }

    private void registerSpawnPoint(SpawnPoint spawnPoint) {
        ArrayList<GameModeTypes> typeList = spawnPoint.typeList;
        for (GameModeTypes type : typeList) {
            ArrayList<SpawnPoint> spawnPointList = spawnPointMap.get(type);
            spawnPointList.add(spawnPoint);
        }
    }

    private void registerGameType(GameModeTypes type) {
        spawnPointMap.put(type, new ArrayList<SpawnPoint>());
    }

    public void spawnEntity(GameModeTypes type, int index, LivingEntity entity) {
        ArrayList<SpawnPoint> spawnPointList = spawnPointMap.get(type);
        SpawnPoint spawnPoint = spawnPointList.get(index);
        spawnPoint.spawnEntity(entity);
    }

    public void spawnEntityRandomly(GameModeTypes type, LivingEntity entity) {
        ArrayList<SpawnPoint> spawnPointList = spawnPointMap.get(type);
        int listLength = spawnPointList.size() - 1;
        int randomIndex = (int) Math.floor(Math.random() * listLength);
        spawnEntity(type, randomIndex, entity);
    }
}