package ru.bmstu.lab3;

import java.io.*;
import java.net.Socket;

import static ru.bmstu.lab3.Server.PORT;
import static ru.bmstu.lab3.Utils.*;

public class Client {

    private static final String HOST = "localhost";

    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        BufferedReader keyboard = null;
        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Соединение с сервером прошло успешно");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Создаем поток для чтения с клавиатуры.
            keyboard = new BufferedReader(new InputStreamReader(System.in));

            while (socket.isConnected()) {
                System.out.println("Генерируем рандомные слова и передаем на сервер");
                System.out.println("Введите минимальный размер слова");
                String min = keyboard.readLine();
                System.out.println("Введите максимальный размер слова");
                String max = keyboard.readLine();

                if (tryParseInt(min) && tryParseInt(max)) {
                    checkStrings(min, max);
                    String randomText = generateRandomString(Integer.parseInt(min), Integer.parseInt(max));
                    sendText(in, out, randomText);
                    createFile(randomText);
                } else {
                    System.out.println("Неверный формат размера слов\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sendText(DataInputStream in, DataOutputStream out, String randomText) {
        String message;
        try {
            out.writeUTF(randomText);
            out.flush();
            System.out.println("Клиент отправил сообщение: " + randomText);
            message = in.readUTF();
            System.out.println(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
