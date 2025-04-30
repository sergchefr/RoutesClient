package ru.ifmo;

import ru.ifmo.commands.*;

import java.util.*;

public class ConsoleIO {
    private Scanner console;
    private CommandManager commandManager;
    private Deque<String> resp;
    private ru.ifmo.ClientManager manager;

    public ConsoleIO() {
        console = new Scanner(System.in);
        //commandVerifier =new CommandVerifier();
        resp = new LinkedList<>();
    }

    public void start(){
        System.out.println("program started. Type commands or \"help\" for help");
        while (true) {

            //выводит все полученные данные
            while (!resp.isEmpty()) {
                System.out.println(resp.pop());
            }

            //ввод команды
            String command = "";
            System.out.print(">>> ");
            try {
                command = console.nextLine();
            } catch (NoSuchElementException e) {
                shutdown();
            }

//            if(deleteExtraSpace(command).equals("help")){
//                System.out.println(commandManager.help());
//                continue;
//            }

            //проверка, какой режим нужен. Запускать ли конструктор?
            command = deleteExtraSpace(command);
            String[] t1 = (command.split(" "));
            if(t1.length==0) continue;
            if(ArrayHasFlag(t1,"-c")){
                String t2 = t1[0];
                var t3 = commandManager.getCommand(t2);
                if(t3==null) {
                    System.out.println("команды с таким именем не существует");
                    continue;
                }
                if(t3.haveFlag("-c")){
                    //доконструирование и отправка
                    command = constructor(command);
                    //System.out.println("выполнение: "+ command);
                    commandManager.execute(command);
                }else System.out.println("команда не поддерживает интерактивный конструктор");
            }else{
                //верификация и отправка

                switch (command.split(" ")[0]){
                    case "help":
                        if(command.split(" ").length==1) System.out.println(commandManager.help());
                        else if (command.split(" ").length==2) System.out.println(commandManager.help(command.split(" ")[1]));
                        else System.out.println("help не принимает больше 1 аргумента");
                        continue;
                    case "exit":
                        shutdown();
                        continue;
                    case "execute_script":
                        if (command.split(" ").length!=2) System.out.println("должен быть 1 аргумент");
                        else commandManager.executeScript(command.split(" ")[1]);
                        continue;

                }

                var t2 = commandManager.getCommand(t1[0]);
                if(t2==null) {
                    System.out.println("команды с таким именем не существует");
                    continue;
                }
                //немой ввод
                if (t1.length == 2 & t2.getParameters().length == 1) {
                    if (!t1[1].contains("=")) {
                        command = t1[0] + " " + t2.getParameters()[0].getName() + "=" + t1[1];
                    }
                }
                //System.out.println(command);
                if(t2.verify(command)){
                    //System.out.println("выполнение: "+ command);
                    commandManager.execute(command);
                }
                else System.out.println("команда введена неверно");
            }
        }
    }

    public String constructor(String prevcom){
        //prevcom = deleteExtraSpace(prevcom.replace(" -c ", " "));
        String[] args =Arrays.copyOfRange(prevcom.split(" "),1,prevcom.split(" ").length-1);

        VerifierCommand com = commandManager.getCommand(prevcom.split(" ")[0]);
        Parameter[] parameters = com.getParameters();

        //проверка на немой ввод
        if(parameters.length==1&args.length==1){
            if(parameters[0].verify(args[0])){
                if(args[0].contains("="))return com.getName()+" "+args[0];
                else return com.getName()+" "+parameters[0].getName()+"="+args[0];
            }
        }

        //основная часть

        //если есть аргументы без "=" - выкидываем.
        LinkedList<String> argLinkedList = new LinkedList<>();
        for (String t1: args) {
            if(t1.contains("=")) argLinkedList.add(t1);
        }

        //парсим аргументы в списке. если они норм, добавляем в билдер пытаемся добавить в билдер.
        CommandBuilder builder = new CommandBuilder(com);
        for (String s : argLinkedList) {
            builder.addParameter(s);
        }

        //через запрос билдера вводим недостающие аргументы в билдер. если все элементы добавлены, билдим команду. возвращаем ее
        Parameter nextParam;
        String input;
        while (!builder.isReady()){
            nextParam = builder.nextParameter();
            System.out.println(nextParam.getName()+":"+nextParam.getLimitations());
            try {
                System.out.print(nextParam.getName()+"=");
                input = console.nextLine();
                builder.addParameter(nextParam.getName()+"="+input.strip());
            } catch (NoSuchElementException e) {
                shutdown();
            }catch (RuntimeException e){
                continue;
            }

        }
        return builder.build();

    }

    public void print(String str){
        resp.addLast(str);
    }

    private void shutdown(){
        System.out.println("emergency exit");
        //manager.exit(1);
        try {
            Thread.sleep(2000);
            System.exit(1);
        } catch (InterruptedException ex) {
            System.exit(2);
        }
    }

    private String deleteExtraSpace(String a) {
       while (a.contains("  ")|a.contains("= ")|a.contains(" =")){
           a=a.replace("  "," ");
           a=a.replace("= ","=");
           a=a.replace(" =","=");
       }
       return a.strip();
    }

    private boolean ArrayHasFlag(String[] args,String flag){
        for (String s : args) {
            if(s.equals(flag)) return true;
        }
        return false;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
