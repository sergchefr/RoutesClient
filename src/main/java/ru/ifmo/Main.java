package ru.ifmo;

import ru.ifmo.commands.Parameter;
import ru.ifmo.commands.VerifierCommandBuilder;

import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ClientManager client = new ClientManager();
        client.start();
        //execute_script C:\Users\sergei\Desktop\прога\lab6\script.txt
//        Socket clientSocket = null; //сокет для общения
//        BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
//        // мы узнаем что хочет сказать клиент?
//        BufferedReader in = null; // поток чтения из сокета
//        BufferedWriter out = null; // поток записи в сокет



//        try {
//            try {
//                clientSocket = new Socket("localhost", 4004);
//                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//                out.write(word + "\n"); // отправляем сообщение на сервер
//                out.flush();
//                String serverWord = in.readLine(); // ждём, что скажет сервер
//                System.out.println(serverWord); // получив - выводим на экран
//            } finally { // в любом случае необходимо закрыть сокет и потоки
//                System.out.println("Клиент был закрыт...");
//                clientSocket.close();
//                in.close();
//                out.close();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }

    }
}
