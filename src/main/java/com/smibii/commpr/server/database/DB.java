package com.smibii.commpr.server.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DB<T extends Serializable> {
    private final Path dbFolder;

    public DB(String folderPath) throws IOException {
        dbFolder = Paths.get(folderPath);
        if (!Files.exists(dbFolder)) {
            Files.createDirectories(dbFolder);
        }
    }

    public void add(String key, T object) throws IOException {
        Path filePath = dbFolder.resolve(key + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(object);
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