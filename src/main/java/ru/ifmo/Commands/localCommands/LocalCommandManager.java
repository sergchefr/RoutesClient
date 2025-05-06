package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.Parameter;
import ru.ifmo.Commands.VerifierCommand;

import java.util.HashMap;

public class LocalCommandManager {
    private HashMap<String, VerifierCommand> verifierCommandHashMap = new HashMap<>();
    private HashMap<String, Icommand> commands = new HashMap<>();

    public String execute(String command){
        var com = commands.get(command.split(" ")[0]);
        if(com==null) return null;
        //System.err.println(com.execute(command));
        return com.execute(command);
    }

    public void addCommand(Icommand icommand){
        commands.put(icommand.getName(),icommand);

    }

    public VerifierCommand getVerifierCommand(String comName) {
        var a = commands.get(comName);
        if(a==null) return null;
        return a.getVerifierCommand();
    }

    public String help(){
        String tmp="";
        for (String s : commands.keySet()) {
            tmp=tmp+s+": "+ getVerifierCommand(s).getDescription()+"\n";
        }
        return tmp.strip();
    }

    public String help(String comName){
        String resp ="";
        var a =  getVerifierCommand(comName);
        if(a==null) return "";
        resp=comName+": "+a.getDescription()+"\nпараметры:\n";
        for (Parameter parameter : a.getParameters()) {
            resp = resp+ parameter.getDescription()+"\n";
        }
        return resp.strip();
    }
}
