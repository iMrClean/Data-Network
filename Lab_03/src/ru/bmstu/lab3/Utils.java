package ru.bmstu.lab3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    private static Random random = new Random((new Date()).getTime());

    public static String generateRandomString(int minLength, int maxLength) {
        char[] values = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'};


        StringBuilder out = new StringBuilder();

        int COUNT_OF_WORLD = 100;

        for (int j = 0; j < COUNT_OF_WORLD; j++) {
            int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
            for (int i = 0; i < length; i++) {
                int randomChar = random.nextInt(values.length);
                out.append(values[randomChar]);
            }
            if (j == COUNT_OF_WORLD - 1) {
                break;
            }
            out.append(", ");
        }
        return out.toString();
    }

    public static void checkStrings(String min, String max) {
        int minLength = Integer.parseInt(min);
        int maxLength = Integer.parseInt(max);
        if (minLength > maxLength) {
            throw new IllegalArgumentException("Минимальное значение больше максимального");
        }
    }

    public static void createFile(String randomString) throws IOException {
        Path path = Paths.get("temp.txt").toAbsolutePath();
        if (!path.getFileName().toFile().exists()) {
            Files.createFile(path);
        }
        Files.write(path, randomString.getBytes(), StandardOpenOption.APPEND);
        System.out.println("Файл успешно записан: " + path + "\n");
    }

    public static boolean tryParseInt(String value) {
        try {
            int result = Integer.parseInt(value);
            return result != 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
