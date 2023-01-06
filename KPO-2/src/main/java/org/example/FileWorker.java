package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileWorker {
    private static List<Path> ourPaths;
    private static Map<String, List<String>> edges = new HashMap<>();
    /*
     Метод, который осуществляет основную работу программы
    */
    public static void working(String path, String answerFile) throws IOException {
        MyFileVisitor fileVisitor = new MyFileVisitor();
        Files.walkFileTree(Path.of(path), fileVisitor);
        ourPaths = fileVisitor.get();
        globalSort();
        writeToFile(answerFile);
    }

    /*
    Основной метод сортировки
    */
    public static void globalSort() throws IOException {
        Path current = Path.of(".").toAbsolutePath();
        String s = "";
        for (Path path : ourPaths) {
            for (String line : Files.readAllLines(path)) {
                if (line.startsWith("require ")) {
                    String dependence = line.substring(8);
                    s = dependence;
                    System.out.println(current.getParent().relativize(path));
                    System.out.println(dependence);
                    System.out.println(path);
                    edges.computeIfAbsent(dependence, a -> new ArrayList<>()).add(current.relativize(path).toString());
                }
            }
        }
        Map<String, Boolean> map = new TreeMap<>();
        if (dfs(s, map)) {
            System.out.println("Был обнаружен цикл: ");
            for (Map.Entry<String, Boolean> e : map.entrySet()) {
                if (e.getValue()) {
                    System.out.println(e.getKey());
                }
            }
        } else {
            List<String> keys = new ArrayList<>(edges.keySet());
            Collections.sort(keys);
            for (String key : keys) {
                System.out.println(key);
                edges.get(key).forEach(System.out::println);
            }
        }
    }
    /*
     Метод для поиска цикла
     */
    public static boolean dfs(String v, Map<String, Boolean> map) {
        map.put(v, true);
        if (!edges.containsKey(v)) {
            return false;
        }
        for (String to : edges.get(v)) {
            if (map.containsKey(to)) {
                return true;
            }
            if (dfs(to, map)) {
                return true;
            }
        }
        map.put(v, false);
        return false;
    }
    /*
     Метод для записи в файл
     */
    public static void writeToFile(String answerFile) throws IOException {
        List<String> keys = new ArrayList<>(edges.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            String s = Files.readString(Path.of(key));
            Files.write(Path.of(answerFile), s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            for (String d : edges.get(key)) {
                s = Files.readString(Path.of(d));
                Files.write(Path.of(answerFile), s.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
        }
    }
}
