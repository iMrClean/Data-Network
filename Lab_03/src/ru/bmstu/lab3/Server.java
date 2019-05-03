package ru.bmstu.lab3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 8082;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket client = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Ждем подключения клиента...");

            client = serverSocket.accept();
            System.out.println("Клиент подключился " + client.getInetAddress());

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            while (!serverSocket.isClosed()) {
                sendMessage(in, out);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sendMessage(DataInputStream in, DataOutputStream out) {
        String line;
        try {
            line = in.readUTF();
            System.out.println("Клиентское сообщение: " + line);
            out.writeUTF("Сервер успешно получил сообщение");
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
