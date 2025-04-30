package ru.ifmo;

import ru.ifmo.commands.CommandManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScriptReader {
    CommandManager commandManager;
    ConsoleIO console;

    public ScriptReader(CommandManager commandManager, ConsoleIO console) {
        this.commandManager = commandManager;
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
            System.out.println(commandManager.getCommand("info"));
            var a = commandManager.getCommand(command.split(" ")[0].strip());
            //System.out.println(a);
            if(a.verify(command)){
                commandManager.execute(command.strip());
            }else{
                console.print("команда введена неверно: "+command);
            }
        }
        System.out.println("чтение файла завершено");
    }

    private String readfile(String scriptLocation) throws IOException{
            Path path = Paths.get(scriptLocation);
            var fileString = Files.readString(path);
            return fileString;
    }
}
