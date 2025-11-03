package lab8;

import java.util.LinkedList;

public class Fifo {
    private final LinkedList<String> buffer = new LinkedList<>();
    private final int MAX_SIZE = 10;

    public synchronized void put(String element) throws InterruptedException {
        while (buffer.size() == MAX_SIZE) {
            System.out.println("Max size");
            wait();
        }
        printThreadInfo("put()");
        buffer.addLast(element);
        notifyAll();
    }

    public synchronized String get() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("Currecntly empty");
            wait();
        }
        printThreadInfo("get()");
        String element = buffer.removeFirst();
        notifyAll();
        return element;
    }

    private void printThreadInfo(String operation) {
        Thread currentThread = Thread.currentThread();
        String info = operation + "(" + currentThread.threadId() + ") :";
        System.out.print(info);
    }
}