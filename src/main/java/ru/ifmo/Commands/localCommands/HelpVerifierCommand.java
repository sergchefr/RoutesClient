package ru.ifmo.Commands.localCommands;

import ru.ifmo.Commands.Parameter;
import ru.ifmo.Commands.VerifierCommand;

import java.util.Arrays;

public class HelpVerifierCommand extends VerifierCommand {

    public HelpVerifierCommand(String name, String description, Parameter[] parameters, String[] flags) {
        super(name, description, parameters, flags);
    }
    @Override
    public boolean verify(String com){
        return com.split(" ").length==1|com.split(" ").length==2;
    }
}
