package ru.ifmo;

import ru.ifmo.commands.CommandManager;
import ru.ifmo.commands.XMLreader;

import java.io.IOException;

public class ClientManager {
    private ConnectionManager connectionManager;
    private XMLreader configReader;
    private ConsoleIO console;
    private CommandManager commandManager;
    private ScriptReader scriptReader;

    public ClientManager() {
        configReader = new XMLreader();
        connectionManager = new ConnectionManager(1111);
        this.console = new ConsoleIO();
        commandManager = new CommandManager(connectionManager,"C:\\Users\\sergei\\Desktop\\прога\\lab6\\config.xml",console);
        console.setCommandManager(commandManager);
        connectionManager.setConsole(console);
        commandManager.setConsole(console);



    }
    public void start(){
        console.start();


//        try {
//            configReader.parceConfig("C:\\Users\\sergei\\IdeaProjects\\RoutesClient\\src\\main\\java\\ru\\ifmo\\config.xml");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    public void exit(int a){
        //connectionManager.addRequest(new Request("exit"));
        System.exit(a);
    }
}
