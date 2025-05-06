package ru.ifmo.excluded;

import ru.ifmo.Commands.serverCommands.ServerCommandManager;
import ru.ifmo.ConsoleIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScriptReader {
    ServerCommandManager serverCommandManager;
    ConsoleIO console;

    public ScriptReader(ServerCommandManager serverCommandManager, ConsoleIO console) {
        this.serverCommandManager = serverCommandManager;
        this.console = console;
    }

    public void readScript(String scriptLocation){
        String fileString;
        try {
            fileString = readfile(scriptLocation);
        }catch (IOException e){
            console.print("ошибка чтения файла");
            return;
        }
        if(fileString.isEmpty()){
            console.print("файл пуст");
            return;
        }
        String[] commands = fileString.split("\n");
        for (String command : commands) {
            //System.out.println(command.split(" ")[0]);
            System.out.println(serverCommandManager.getVerifierCommand("info"));
            var a = serverCommandManager.getVerifierCommand(command.split(" ")[0].strip());
            //System.out.println(a);
            if(a.verify(command)){
                serverCommandManager.execute(command.strip());
            }else{
                console.print("команда введена неверно: "+command);
            }
        }
        console.print("чтение файла завершено");
    }

    private String readfile(String scriptLocation) throws IOException{
            Path path = Paths.get(scriptLocation);
            var fileString = Files.readString(path);
            return fileString;
    }
}
