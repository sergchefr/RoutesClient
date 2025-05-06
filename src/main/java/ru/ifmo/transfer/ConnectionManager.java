package ru.ifmo.transfer;

import ru.ifmo.Commands.serverCommands.NoConfigException;
import ru.ifmo.ConsoleIO;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ConnectionManager implements IconnectionManager{
    private int port;
    private ConsoleIO console;
    public ConnectionManager(int port) {
        this.port = port;
    }

    public String getConfig()throws NoConfigException {
        var a =getResponse("load_config");
        if(a.split("\n")[0].strip().equals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")) return a;
        //console.print("ошибка получения конфига с сервера");
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
        Request request = new Request(command);
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
            return "ошибка соединения с сервером, запрос не отправлен";
        }
        return "неизвестная ошибка";
    }


}