package ru.bmstu.lab4;

import java.io.*;
import java.net.Socket;

import static ru.bmstu.lab4.Server.FILENAME;
import static ru.bmstu.lab4.Server.PORT;
import static ru.bmstu.lab4.Utils.*;

public class Client {
    //На стороне клиента генерируем бинарный файл и отправляем на сервер
    private static final String HOST = "localhost";

    public static void main(String[] args) {

        Socket socket = null;
        BufferedReader keyboard = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        ObjectOutputStream out = null;
        DataInputStream in = null;
        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Соединение с сервером прошло успешно");
            while (socket.isConnected()) {

                //Записываем какой-то текст вводя с клавиатуры
                keyboard = new BufferedReader(new InputStreamReader(System.in));
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());

                System.out.println("Введите сообщение на сервер:");
                String message = keyboard.readLine();

                //Создаем файл и записываем то, что ввели
                createFile(message);

                // Считаем файл в буфер
                byte[] buf = readFile();
                String text = new String(buf);

                //Переводим текст в бинарный
                String binText = convertStringToBinary(text);

                //Отправляем на сервер
                sendToServer(binText.getBytes(), out);

                //Ждем ответ от сервера
                String serverMessage = in.readUTF();
                System.out.println("Ответ от сервера: " + serverMessage);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sendToServer(byte[] buf, ObjectOutputStream out) throws IOException {

        Data data = new Data();
        data.setName(FILENAME);
        data.setBytes(byteToObject(buf));

        out.writeObject(data);
        out.flush();
        System.out.println("Клиент отправил файл " +
                data.getName() +
                " размером (" + data.getBytes().length +
                " байт)");
    }
}
