package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.ParameterBuilder;
import ru.ifmo.Commands.VerifierCommand;
import ru.ifmo.Commands.VerifierCommandBuilder;
import ru.ifmo.ConsoleIO;
import ru.ifmo.transfer.ConnectionManager;

public class RegisterCommand implements Icommand{
    private VerifierCommand verifierCommand;

    public RegisterCommand() {
        var builder = new VerifierCommandBuilder();
        var paramBuilder = new ParameterBuilder();
        builder.setName(getName());
        builder.setDescription("регистрирует нового пользователя");
        builder.addFlag("-c");

        paramBuilder.setName("username").setLimitations("String").setDescription("имя пользователя");
        builder.addParamameter(paramBuilder.getParameter());
        paramBuilder.setName("password").setLimitations("String").setDescription("пароль");
        builder.addParamameter(paramBuilder.getParameter());
        verifierCommand = builder.build();
    }

    @Override
    public String execute(String command) {
        ConnectionManager.getInstance().executeOnServer(command);
        return "";
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public VerifierCommand getVerifierCommand() {
        return verifierCommand;
    }
}
