package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.Parameter;
import ru.ifmo.Commands.IcommandManager;
import ru.ifmo.Commands.VerifierCommand;
import ru.ifmo.Commands.VerifierCommandBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExecuteScriptCommand implements Icommand{
    private IcommandManager executor;
    private VerifierCommand verifierCommand;

    public ExecuteScriptCommand(IcommandManager executor) {
        this.executor = executor;
        var builder = new VerifierCommandBuilder();
        builder.setName(getName());
        builder.addParamameter(new Parameter("filename","String","путь к скрипту"));
        builder.addFlag("-c");
        builder.setDescription("выполняет указанный скрипт. Может содержать как любые команды, вводимые в консоль");
        verifierCommand = builder.build();
    }

    @Override
    public String execute(String command) {
        readScript(command.split(" ")[1].strip());
        return "чтение скрипта завершено";
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public VerifierCommand getVerifierCommand() {
        return verifierCommand;
    }

    private String readfile(String scriptLocation) throws Exception {
        Path path;
        try {
            path= Paths.get(scriptLocation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var fileString = Files.readString(path);
        return fileString;
    }

    private void readScript(String scriptLocation){
        String fileString;
        try {
            fileString = readfile(scriptLocation.split("=")[1].strip());
        }catch (Exception e){
            executor.addAnswer("ошибка чтения файла ");
            return;
        }
        if(fileString.isEmpty()){
            executor.addAnswer("файл пуст");
            return;
        }
        String[] commands = fileString.split("\n");
        for (String command : commands) {
            if (command.isBlank()) continue;
            var a = executor.getVerifierCommand(command.split(" ")[0].strip());
            if(a==null){
                executor.addAnswer("команда не найдена: "+ command);
                continue;
            }
            if(a.verify(command)){
                executor.execute(command.strip());
            }else{
                executor.addAnswer("команда введена неверно: "+command);
            }
        }
    }
}
