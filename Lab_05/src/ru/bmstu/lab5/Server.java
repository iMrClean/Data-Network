package ru.bmstu.lab5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 8080;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setReuseAddress(true);

            System.out.println("Ждем подключения клиента...");

            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                System.out.println("Клиент подключился " + client.getInetAddress());
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket client;

        public ClientHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            DataOutputStream out = null;
            DataInputStream in = null;
            try {
                out = new DataOutputStream(client.getOutputStream());
                in = new DataInputStream(client.getInputStream());
                String message;
                while (client.isConnected()) {
                    message = in.readUTF();
                    long id = Thread.currentThread().getId() - 10;
                    System.out.println("Номер потока: " + id + " Клиентское сообщение: " + message);
                    out.writeUTF(message);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
