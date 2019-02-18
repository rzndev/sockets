package ru.sbt.javaschool.socket;

import java.io.*;
import java.net.Socket;

public class MainClient extends Thread{
    private static final String DEFAULT_HOST="127.0.0.1";
    private static final int DEFAULT_PORT=80;
    private static ObjectOutputStream out; // поток записи в сокет
    private int id;

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for(int i = 0; i < 10; i++) {
            MainClient client = new MainClient();
            client.id = i;
            threads[i] = client;
            client.start();
        }
        for(int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Socket sock = null;

        FallBack fallBack = new FallBack();
        fallBack.setMessage("HELLO " + id + "!");

        try {
            sock = new Socket(DEFAULT_HOST, DEFAULT_PORT);
            out = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
            out.writeObject(fallBack);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
