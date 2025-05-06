package ru.ifmo.transfer;

import ru.ifmo.Commands.serverCommands.NoConfigException;
import ru.ifmo.ConsoleIO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class NBconnectionManager implements IconnectionManager {
    private ArrayList<Response> responses= new ArrayList<>();

    private SocketChannel socketChannel;
    private int port = 1111;
    private String adress = "localhost";
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public NBconnectionManager() {

    }

    @Override
    public String getConfig() throws NoConfigException {
//        public String getConfig()throws NoConfigException {
//            var a =getResponse("load_config");
//            if(a.split("\n")[0].strip().equals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")) return a;
//            //console.print("ошибка получения конфига с сервера");
//            throw new NoConfigException("ошибка получения конфига с сервера");
//        }
        return null;
    }

    @Override
    public void setConsole(ConsoleIO console) {

    }

    @Override
    public void executeOnServer(String command) {

    }

    @Override
    public String[] getResponses() {
        String[] resps = new String[responses.size()];
        for (int i = 0; i < responses.size(); i++) {
            resps[i] = responses.get(i).getAnswer();
        }
        return resps;
    }

    private void connect()throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(adress,port));
//        objectInputStream = socketChannel.
    }

    private void disconnect()throws IOException{
        socketChannel.close();
    }

    private void checkResponses(){

    }
}