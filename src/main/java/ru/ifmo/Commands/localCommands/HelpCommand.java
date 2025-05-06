package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.*;

public class HelpCommand implements Icommand{
    private CommandManagerImpl executor;
    private VerifierCommand verifierCommand;

    public HelpCommand(CommandManagerImpl executor) {
        this.executor = executor;
        verifierCommand=new HelpVerifierCommand(getName(),
                "выводит описание команды. При отсутствии аргумента выводит список всех команд с их описанием",
                new Parameter[]{new Parameter("comName","String","название команды")},
                new String[]{});
    }

    @Override
    public String execute(String command) {
        if(command.split(" ").length==1) return (executor.localhelp()+"\n"+executor.serverhelp()).strip();
        String comName =command.split(" ")[1].split("=")[1].strip();
        if(command.split(" ").length==2) return (executor.localhelp(comName)+"\n"+executor.serverhelp(comName)).strip();
        return "";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public VerifierCommand getVerifierCommand() {
        return verifierCommand;
    }
}
