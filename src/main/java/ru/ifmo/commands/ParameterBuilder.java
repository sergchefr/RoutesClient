package ru.ifmo.commands;

public class ParameterBuilder {
    private String name="";
    private String description="";
    private String limitations;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLimitations(String limitations) {
        this.limitations = limitations;
    }

    public Parameter getParameter(){
        if(description.isEmpty()) description="нет описания для данного параметра";
        if(name.isEmpty()) throw new IllegalArgumentException("у класса должно быть имя");
        return new Parameter(name,limitations,description);
    }
}
