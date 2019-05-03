package ru.bmstu.lab6;

import java.io.*;
import java.net.Socket;

import static ru.bmstu.lab6.Server.PORT;

public class Client {

    private static final String HOST = "localhost";

    public static void main(String[] args) {
        Socket client = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        BufferedReader keyboard = null;
        try {
            client = new Socket(HOST, PORT);

            keyboard = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());

            while (client.isConnected()) {
                sendMessage(out, in, keyboard);
            }
            in.close();
            out.close();
            keyboard.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(DataOutputStream out, DataInputStream in, BufferedReader keyboard) {
        String message;
        try {
            System.out.println("Введите сообщение на сервер:");
            message = keyboard.readLine();
            out.writeUTF(message);
            out.flush();
            message = in.readUTF();
            System.out.println("Ответ от сервера: " + message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}