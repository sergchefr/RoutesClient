package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.VerifierCommand;

public interface Icommand {
    String execute(String command);
    String getName();
    VerifierCommand getVerifierCommand();
}
