package com.chmnu_ki_123.c3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {

    public static Path copyFile(String sourceFilePath, String targetDirPath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File targetDir = new File(targetDirPath);

        if (!sourceFile.exists()) {
            throw new IOException("Файл-джерело не існує: " + sourceFilePath);
        }
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            throw new IOException("Не вдалося створити директорію: " + targetDirPath);
        }
        if (!targetDir.isDirectory()) {
            throw new IOException("Цільовий шлях не є директорією: " + targetDirPath);
        }

        Path targetFilePath = targetDir.toPath().resolve(sourceFile.getName());
        Files.copy(sourceFile.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
        return targetFilePath;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Використання: java Main <sourceFilePath> <targetDirPath>");
            return;
        }

        String sourceFilePath = args[0];
        String targetDirPath = args[1];

        try {
            Path copiedFile = copyFile(sourceFilePath, targetDirPath);
            System.out.println("Файл успішно скопійовано до: " + copiedFile);
        } catch (IOException e) {
            System.err.println("Помилка під час копіювання: " + e.getMessage());
        }
    }
}
