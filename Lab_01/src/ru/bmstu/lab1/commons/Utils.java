package ru.bmstu.lab1.commons;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String PATTERN_VALUE = "(\\d{0,3}).(\\d{0,3}).(\\d{0,3}).(\\d{0,3})";

    public static void getResult(int num, List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (num == 1) {
                //2
                System.out.print(Integer.toBinaryString(numbers.get(i)) + ", ");
            }
            if (num == 2) {
                //8
                System.out.print(Integer.toOctalString(numbers.get(i)) + ", ");
            }
            if (num == 3) {
                //16
                System.out.print(Integer.toHexString(numbers.get(i)) + ", ");
            }
        }
    }

    public static void findIpAddress(List<Integer> numbers, List<String> lines) {
        Pattern pattern = Pattern.compile(PATTERN_VALUE);
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                numbers.add(Integer.parseInt(m.group(1)));
                numbers.add(Integer.parseInt(m.group(2)));
                numbers.add(Integer.parseInt(m.group(3)));
                numbers.add(Integer.parseInt(m.group(4)));
            }
        }
    }
}
