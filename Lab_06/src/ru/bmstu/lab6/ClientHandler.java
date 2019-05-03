package ru.bmstu.lab6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

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
            System.out.println(e.getMessage());
        } finally {
            try {
                if (out != null) {
                    System.out.println("Закрываем выходящий поток");
                    out.close();
                }
                if (in != null) {
                    System.out.println("Закрываем входящий поток");
                    in.close();
                }
                if (client != null) {
                    System.out.println("Закрываем клиент");
                    client.close();
                }
            } catch (IOException e) {
                System.out.println("Не удалось закрыть клиент");
                System.out.println(e.getMessage());
            }
        }
    }
}
