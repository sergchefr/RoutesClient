package ru.ifmo.transfer;

import ru.ifmo.Commands.serverCommands.NoConfigException;
import ru.ifmo.ConsoleIO;

public interface IconnectionManager {

    String getConfig()throws NoConfigException;
    void setConsole(ConsoleIO console);
    void executeOnServer(String command);
    String[] getResponses();
}
