package ru.bmstu.lab6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 8080;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Ждем подключения клиента...");

            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                System.out.println("Клиент подключился " + client.getInetAddress().getHostAddress());
                ClientHandler clientSocket = new ClientHandler(client);
                new Thread(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Непредвиденная ошибка сервера");
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    System.out.println("Закрываем сокет");
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Не удалось закрыть сокет");
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}