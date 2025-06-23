package ru.ifmo;

//TODO сделать подключение постоянным: необходима возможность отправлять и получать сообщения частями
//TODO локальные команды возвращают void
//TODO при шатдауне отключаться от сервера
public class Main {
    public static void main(String[] args) {
        ClientManager client = new ClientManager();
        client.start();
        //execute_script C:\Users\sergei\Desktop\прога\lab6\script.txt
    }
}
