package lab8;

public class Producer implements Runnable {
    private final String name;
    private int counter = 0;
    private final Fifo fifo;
    private int wait_time;

    public Producer(String name, Fifo fifo, int time) {
        this.name = name;
        this.fifo = fifo;
        this.wait_time = time;
    }

    @Override
    public void run() {
        while (true) {
            try {
                long time = System.currentTimeMillis() % 100000;
                String message = this.name + " " + counter + " " + time;
                fifo.put(message);
                System.out.println("produced " + message);
                counter++;
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}