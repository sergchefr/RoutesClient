package ru.ifmo;

import ru.ifmo.Commands.CommandManagerImpl;
import ru.ifmo.Commands.localCommands.ExecuteScriptCommand;
import ru.ifmo.Commands.localCommands.ExitCommand;
import ru.ifmo.Commands.localCommands.HelpCommand;
import ru.ifmo.Commands.localCommands.LocalCommandManager;
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
        connectionManager = new ConnectionManager(1111);
        this.console = new ConsoleIO();
        LocalCommandManager localCommandManager = new LocalCommandManager();
        ServerCommandManager serverCommandManager = new ServerCommandManager(connectionManager,"resources/config.xml",console);
        commandManager = new CommandManagerImpl(localCommandManager,serverCommandManager);
        console.setCommandManager(commandManager);
        connectionManager.setConsole(console);

        localCommandManager.addCommand(new ExecuteScriptCommand(commandManager));
        localCommandManager.addCommand(new ExitCommand(console));
        localCommandManager.addCommand(new HelpCommand(commandManager));
    }
    public void start(){
        console.start();
    }
}
