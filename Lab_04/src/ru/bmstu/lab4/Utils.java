package ru.bmstu.lab4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;

import static ru.bmstu.lab4.Server.FILENAME;
import static ru.bmstu.lab4.Server.SERVER_FILE;

public class Utils {

    public static String convertStringToBinary(String text) {
        byte[] data = text.getBytes();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int val = data[i];
            for (int j = 0; j < 8; j++) {
                sb.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            sb.append(' ');
        }
        System.out.println("'" + text + "' to binary: " + sb);
        return sb.toString();
    }

    public static byte[] byteToPrimitive(Byte[] bytesObject) {
        byte[] bytes = new byte[bytesObject.length];
        IntStream.range(0, bytesObject.length).forEachOrdered(i -> bytes[i] = bytesObject[i]);
        return bytes;
    }

    public static Byte[] byteToObject(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        int i = 0;
        for (byte b : bytesPrim) {
            bytes[i++] = b;
        }
        return bytes;
    }

    public static byte[] readFile() throws IOException {
        FileInputStream fis;
        fis = new FileInputStream(FILENAME);
        byte[] buf = new byte[fis.available()];
        fis.read(buf);
        fis.close();
        return buf;
    }

    public static void createFile(String text) throws IOException {
        FileOutputStream fos;
        fos = new FileOutputStream(FILENAME);
        fos.write(text.getBytes());
        fos.close();
    }

    public static void createFile(Byte[] bytes) throws IOException {
        FileOutputStream fos;
        fos = new FileOutputStream(SERVER_FILE);
        fos.write(byteToPrimitive(bytes));
        fos.close();
    }
}

