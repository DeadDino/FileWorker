package org.example;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    List<Path> ourList = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.getFileName().toString().endsWith(".txt") || file.getFileName().toString().endsWith(".TXT")) {
            ourList.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
        System.out.println("Не удалось открыть файл.");
        return FileVisitResult.SKIP_SUBTREE;
    }

    public List<Path> get() {
        return ourList;
    }
}
