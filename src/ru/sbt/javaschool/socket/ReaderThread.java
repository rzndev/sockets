package ru.sbt.javaschool.socket;

import java.util.concurrent.BlockingQueue;

public class ReaderThread extends Thread {
    private BlockingQueue<String> queue;

    public void setQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (!queue.isEmpty()) {
                System.out.println(queue.remove());
                Thread.yield();
            }
        }
    }
}
