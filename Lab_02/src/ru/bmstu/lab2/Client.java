package ru.bmstu.lab2;
import java.io.*;
import java.net.Socket;

import static ru.bmstu.lab2.Server.PORT;

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
                System.out.println("Введите сообщение на сервер: ");
                sendMessage(in, out, keyboard);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sendMessage(DataInputStream in, DataOutputStream out, BufferedReader keyboard) {
        String message;
        try {
            message = keyboard.readLine();
            out.writeUTF(message);
            out.flush();
            System.out.println("Клиент отправил сообщение: " + message);
            message = in.readUTF();
            System.out.println(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
