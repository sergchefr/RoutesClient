package ru.ifmo;

import ru.ifmo.Commands.CommandManagerImpl;
import ru.ifmo.Commands.localCommands.*;
import ru.ifmo.Commands.serverCommands.ServerCommandManager;
import ru.ifmo.Commands.serverCommands.XMLreader;
import ru.ifmo.transfer.ConnectionManager;

public class ClientManager {
    private ConnectionManager connectionManager;
    private XMLreader configReader;
    private ConsoleIO console;
    private CommandManagerImpl commandManager;

    public ClientManager() {
        configReader = new XMLreader();

        connectionManager = ConnectionManager.getInstance();
        connectionManager.setPort(5317);

        this.console = ConsoleIO.getInstance();
        ServerCommandManager serverCommandManager = ServerCommandManager.getInstance();
        LocalCommandManager localCommandManager = new LocalCommandManager();
        //ServerCommandManager serverCommandManager = new ServerCommandManager(connectionManager,"resources/config.xml",console);
        commandManager = new CommandManagerImpl(localCommandManager,serverCommandManager);
        console.setCommandManager(commandManager);
        connectionManager.setConsole(console);

        localCommandManager.addCommand(new ExecuteScriptCommand(commandManager));
        localCommandManager.addCommand(new ExitCommand(console));
        localCommandManager.addCommand(new HelpCommand(commandManager));
        localCommandManager.addCommand(new RegisterCommand());
        localCommandManager.addCommand(new LoginCommand());
    }
    public void start(){
        console.start();
    }
}
