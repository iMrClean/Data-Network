package ru.bmstu.lab1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ru.bmstu.lab1.commons.Utils.findIpAddress;
import static ru.bmstu.lab1.commons.Utils.getResult;

public class Main {

    private static final String FILE_NAME = "ip.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input number of: \"1\" to Bin \"2\" to Oct  \"3\" to Hex");
        int num = scanner.nextInt();
        if (num < 1 || num > 3) {
            throw new IllegalArgumentException();
        }
        List<Integer> numbers = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);

        findIpAddress(numbers, lines);
        getResult(num, numbers);
    }
}
