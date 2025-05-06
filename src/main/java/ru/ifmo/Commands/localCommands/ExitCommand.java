package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.Parameter;
import ru.ifmo.Commands.VerifierCommand;
import ru.ifmo.Commands.VerifierCommandBuilder;
import ru.ifmo.ConsoleIO;

public class ExitCommand implements Icommand{
    private ConsoleIO console;
    private VerifierCommand verifierCommand;

    public ExitCommand(ConsoleIO console) {
        this.console = console;
        var builder = new VerifierCommandBuilder();
        builder.setName(getName());
        builder.setDescription("завершает работу приложения");
        verifierCommand = builder.build();
    }

    @Override
    public String execute(String command) {
        console.print("exiting...");
        try {
            Thread.sleep(2000);
            System.exit(1);
        } catch (InterruptedException ex) {
            System.exit(2);
        }
        return "";
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public VerifierCommand getVerifierCommand() {
        return verifierCommand;
    }
}
