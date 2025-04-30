package ru.ifmo.commands;

import ru.ifmo.ConnectionManager;
import ru.ifmo.ConsoleIO;
import ru.ifmo.NoConfigException;
import ru.ifmo.ScriptReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Необходим для обработки и верификации команд.
 */
public class CommandManager {
    private HashMap<String, VerifierCommand> commandHashMap = new HashMap<>();
    private ConsoleIO console;
    private ConnectionManager connectionManager;
    private boolean hasConfig;
    private String configFileName;

    public CommandManager(ConnectionManager connectionManager,String configFileName,ConsoleIO console) {
        this.console = console;
        this.configFileName = configFileName;
        this.connectionManager=connectionManager;
        hasConfig=false;
        XMLreader configreader = new XMLreader();

        try {
            String config = connectionManager.getConfig();
            Path path = Paths.get(configFileName);
            if(Files.exists(path)) Files.delete(path);
            Files.createFile(path);
            var fileString = Files.writeString(path,config);
            //System.out.println(fileString);
            //System.out.println(config);
            console.print("конфиг с сервера загружен");
            hasConfig=true;

            VerifierCommand[] commands=  configreader.parceConfig(configFileName);
            for (VerifierCommand command : commands) {
                commandHashMap.put(command.getName(),command);
            }
        } catch (NoConfigException e) {
            console.print(e.getMessage());
        }catch (IOException ex){
            console.print("ошибка сохранения конфига");
        }

//        try{
//            VerifierCommand[] commands=  configreader.parceConfig(configFileName);
//            for (VerifierCommand command : commands) {
//                commandHashMap.put(command.getName(),command);
//            }
//        } catch (IOException e) {
//
//        }
    }

    public String help(){
        String tmp="";
        for (String s : commandHashMap.keySet()) {
            tmp=tmp+s+": "+commandHashMap.get(s).getDescription()+"\n";
        }
        return tmp.strip();
    }

    public String help(String comName){
        String resp ="";
        var a =  commandHashMap.get(comName);
        if(a==null) return "не существует команды с таким названием";
        resp=comName+": "+a.getDescription()+"\nпараметры:\n";
        for (Parameter parameter : a.getParameters()) {
            resp = resp+ parameter.getDescription()+"\n";
        }
        return resp.strip();
    }

    public void executeScript(String fileName){
        ScriptReader scriptReader = new ScriptReader(this,console);
        scriptReader.readScript(fileName);
    }

    public VerifierCommand getCommand(String comName)throws IllegalArgumentException{
        //if(a==null) throw new IllegalArgumentException();
        return commandHashMap.get(comName);
    }

    public void execute(String command){

        connectionManager.executeOnServer(command);

    }

    public void setConsole(ConsoleIO console) {
        this.console = console;
    }
}
