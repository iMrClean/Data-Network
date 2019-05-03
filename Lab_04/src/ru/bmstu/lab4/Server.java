package ru.bmstu.lab4;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static ru.bmstu.lab4.Utils.createFile;

public class Server {
    // На стороне сервера задаем имя файла и его размер
    public static final int PORT = 8082;
    private static final int MAX_BUF_SIZE = 1024;
    public static final String FILENAME = "client_file.txt";
    public static final String SERVER_FILE = "server_file.txt";

    public static void main(String[] args) {

        Socket client = null;
        ServerSocket serverSocket = null;
        ObjectInputStream in = null;
        DataOutputStream out = null;
        Data data = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Ждем подключения клиента...");

            client = serverSocket.accept();
            System.out.println("Клиент подключился " + client.getInetAddress());
            while (!serverSocket.isClosed()) {
                out = new DataOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());

                data = (Data) in.readObject();
                boolean flag = checkInputStream(out, data);
                if (flag) {
                    String message = "Сервер успешно получил данные";
                    System.out.println(message);
                    out.writeUTF(message);
                    createFile(data.getBytes());
                    System.out.println("Сервер успешно записал данные в файл");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean checkInputStream(DataOutputStream out, Data data) throws IOException {

        String message;
        boolean result = true;

        if (data.getBytes().length > MAX_BUF_SIZE) {
            message = "Первышен допустимый размер файла: " +
                    data.getBytes().length +
                    ", максимальный размер: " + MAX_BUF_SIZE + " байт";
            System.out.println(message);
            out.writeUTF(message);
            result = false;
        }

        if (!data.getName().equals(FILENAME)) {
            message = "Неверное имя файла";
            System.out.println(message);
            out.writeUTF(message);
            result = false;
        }
        return result;
    }
}
