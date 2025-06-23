package ru.ifmo.Commands.serverCommands;

import ru.ifmo.Commands.VerifierCommand;
import ru.ifmo.Commands.Parameter;
import ru.ifmo.transfer.ConnectionManager;
import ru.ifmo.ConsoleIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Необходим для обработки и верификации команд.
 */
public class ServerCommandManager {
    private HashMap<String, VerifierCommand> verifierCommandHashMap = new HashMap<>();
    private ConsoleIO console;
    private ConnectionManager connectionManager;
    private boolean hasConfig;
    private String configFileName;
    private ArrayList<String> responses = new ArrayList<>();
    private static ServerCommandManager instance;

    private ServerCommandManager(String configFileName) {
        this.console = ConsoleIO.getInstance();
        this.configFileName = configFileName;
        this.connectionManager=ConnectionManager.getInstance();
        hasConfig=false;
        //loadConfig();
    }

    public String help(){
        String tmp="";
        //System.out.println(verifierCommandHashMap.keySet().size());
        for (String s : verifierCommandHashMap.keySet()) {
            tmp=tmp+s+": "+ verifierCommandHashMap.get(s).getDescription()+"\n";
        }
        //System.out.println(tmp.strip());
        return tmp.strip();
    }

    public String help(String comName){
        String resp ="";
        var a =  verifierCommandHashMap.get(comName);
        if(a==null) return "";
        resp=comName+": "+a.getDescription()+"\nпараметры:\n";
        for (Parameter parameter : a.getParameters()) {
            resp = resp+ parameter.getDescription()+"\n";
        }
        return resp.strip();
    }

    public VerifierCommand getVerifierCommand(String comName)throws IllegalArgumentException{
        //if(a==null) throw new IllegalArgumentException();
        return verifierCommandHashMap.get(comName);
    }

    public void execute(String command){
        connectionManager.executeOnServer(command);
    }

    public void addResponse(String response){
        responses.add(response);
    }

    public String[] getResponses() {
        return responses.toArray(new String[]{});
    }

    public void loadConfig(){
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
                verifierCommandHashMap.put(command.getName(),command);
            }
        } catch (NoConfigException e) {
            console.print(e.getMessage());
        }catch (IOException ex){
            console.print("ошибка сохранения конфига");
        }
    }

    public static ServerCommandManager getInstance(){
        if(instance==null){
            instance = new ServerCommandManager("resources/config.xml");
        }
        return instance;
    }

}
