package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.ParameterBuilder;
import ru.ifmo.Commands.VerifierCommand;
import ru.ifmo.Commands.VerifierCommandBuilder;
import ru.ifmo.Commands.serverCommands.ServerCommandManager;
import ru.ifmo.transfer.ConnectionManager;

public class LoginCommand implements Icommand{
    private VerifierCommand verifierCommand;
    private ConnectionManager connectionManager;
    private String username;
    private String password;

    public LoginCommand() {
        connectionManager = ConnectionManager.getInstance();
        var builder = new VerifierCommandBuilder();
        var paramBuilder = new ParameterBuilder();
        builder.setName(getName());
        builder.setDescription("подключает к серверу");
        builder.addFlag("-c");

        paramBuilder.setName("username").setLimitations("String").setDescription("имя пользователя");
        builder.addParamameter(paramBuilder.getParameter());
        paramBuilder.setName("password").setLimitations("String").setDescription("пароль");
        builder.addParamameter(paramBuilder.getParameter());
        verifierCommand = builder.build();
    }

    @Override
    public String execute(String command) {
        parcecommand(command);
        if(password==null| username==null)return "команда введена неверно";
        connectionManager.setPassword(password);
        connectionManager.setUsername(username);
        password=null;
        username=null;
        ServerCommandManager.getInstance().loadConfig();
        return "";
    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public VerifierCommand getVerifierCommand() {
        return verifierCommand;
    }

    private void parcecommand(String command){
        var args = command.strip().split(" ");
        for (String arg : args) {
            switch (arg.split("=")[0]) {
                case "username":
                    username = arg.split("=")[1];
                    break;
                case "password":
                    password = arg.split("=")[1];
                    break;
            }
        }
    }
}
