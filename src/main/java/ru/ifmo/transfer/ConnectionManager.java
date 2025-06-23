package ru.ifmo.transfer;

import org.w3c.dom.ls.LSOutput;
import ru.ifmo.Commands.serverCommands.NoConfigException;
import ru.ifmo.ConsoleIO;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ConnectionManager implements IconnectionManager{
    private int port;
    private ConsoleIO console;

    private String username;
    private String password;

    private static ConnectionManager instance;

    private ConnectionManager(int port) {
        this.port = port;
    }

    public String getConfig()throws NoConfigException {
        var a =getResponse("load_config");
        if(a.split("\n")[0].strip().equals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")) return a;
        if(a.equals("ошибка доступа")) console.print("неверный логин/пароль");
        //System.out.println(a);
        //console.print("ошибка получения конфига с сервера");
        //System.out.println(a+":client.connmann");
        throw new NoConfigException("ошибка получения конфига с сервера");
    }

    @Override
    public String[] getResponses() {// не работает
        return new String[0];
    }

    public void setConsole(ConsoleIO console) {
        this.console = console;
    }

    public void executeOnServer(String command) {
        console.print(getResponse(command));
    }

    private String getResponse(String command){
        Request request = new Request(command,username,password);
        try (SocketChannel sc = SocketChannel.open()) {
            sc.configureBlocking(true);
            sc.connect(new InetSocketAddress("localhost", port));

            //ByteBuffer buf = ByteBuffer.wrap(command.getBytes());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(request);

            ByteBuffer buf = ByteBuffer.wrap(baos.toByteArray());
            sc.write(buf);


            ByteBuffer responseBuffer = ByteBuffer.allocate(100000);
            int r = sc.read(responseBuffer);
            if (r == -1) {
                sc.close();
                //System.out.println("client closed");
            } else {
                var inbuf = new byte[r];
                responseBuffer.get(0, inbuf);
                ByteArrayInputStream bais = new ByteArrayInputStream(inbuf);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Response response = (Response) ois.readObject();
                return response.getAnswer();
            }

        } catch (IOException|ClassNotFoundException e) {
            System.out.println(e);
            return "ошибка соединения с сервером, запрос не отправлен";
        }
        return "неизвестная ошибка";
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ConnectionManager getInstance() {
        if(instance==null){
            instance = new ConnectionManager(1111);
        }
        return instance;
    }

    public void setPort(int port) {
        this.port = port;
    }
}