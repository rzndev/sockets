package ru.sbt.javaschool.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main extends Thread {

    private Socket client;

    private BlockingQueue<String> queue;

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ReaderThread reader = new ReaderThread();
        reader.setQueue(queue);
        reader.start();
        while(true) {
            try (ServerSocket sock = new ServerSocket(80)) {
                Main process = new Main();
                process.client = sock.accept();
                process.queue = queue;
                process.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        FallBack fallBack = new FallBack();
        fallBack.setMessage("");
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()))) {
            try {
                fallBack = (FallBack) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            queue.put(fallBack.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.yield();
        //System.out.println(fallBack.getMessage());
    }
}
