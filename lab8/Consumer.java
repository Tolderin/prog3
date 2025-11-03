package lab8;

public class Consumer extends Thread {
    private final Fifo fifo;
    private final String name;
    private final int sleepTimeMillis;

    public Consumer(Fifo f, String s, int n) {
        this.fifo = f;
        this.name = s;
        this.sleepTimeMillis = n;
    }

    @Override
    public void run() {
        while (true) {
            String consumedMessage = null;
            try {
                consumedMessage = fifo.get();
                long time = System.currentTimeMillis() % 100000;
                System.out.println(" consumed " + name + " " + consumedMessage + " " + time);
                Thread.sleep(sleepTimeMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(name + " interrupted");
                break;
            }
        }
    }
}