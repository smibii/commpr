package com.smibii.commpr.server.database;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantLock;

public class DB<T extends Serializable> {
    private final Path dbFolder;
    private final ReentrantLock lock = new ReentrantLock();

    public DB(String folderPath, ServerLevel level) throws IOException {
        dbFolder = level.getServer().getWorldPath(LevelResource.ROOT).resolve(folderPath);
        if (!Files.exists(dbFolder)) {
            Files.createDirectories(dbFolder);
        }
    }

    public void add(String key, T object) throws IOException {
        lock.lock();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFolder.resolve(key + ".dat").toFile()))) {
            oos.writeObject(object);
        } finally {
            lock.unlock();
        }
    }

    public T get(String key) throws IOException, ClassNotFoundException {
        Path filePath = dbFolder.resolve(key + ".dat");
        if (!Files.exists(filePath)) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (T) ois.readObject();
        }
    }

    public boolean remove(String key) throws IOException {
        Path filePath = dbFolder.resolve(key + ".dat");
        return Files.deleteIfExists(filePath);
    }

    public boolean exists(String key) {
        Path filePath = dbFolder.resolve(key + ".dat");
        return Files.exists(filePath);
    }
}