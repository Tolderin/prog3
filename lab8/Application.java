package lab8;

public class Application {
    public static void main(String[] args) {
        Fifo sharedFifo = new Fifo();

        int producer_wait = 1000;
        int consumer_wait = 100;
        Producer producer1 = new Producer("Producer1", sharedFifo, producer_wait);
        Producer producer2 = new Producer("Producer2", sharedFifo, producer_wait);
        Producer producer3 = new Producer("Producer3", sharedFifo, producer_wait);

        Thread producerThread1 = new Thread(producer1);
        Thread producerThread2 = new Thread(producer2);
        Thread producerThread3 = new Thread(producer3);

        Consumer consumer1 = new Consumer(sharedFifo, "Consumer1", consumer_wait);
        Consumer consumer2 = new Consumer(sharedFifo, "Consumer2", consumer_wait);
        Consumer consumer3 = new Consumer(sharedFifo, "Consumer3", consumer_wait);
        Consumer consumer4 = new Consumer(sharedFifo, "Consumer4", consumer_wait);

        producerThread1.start();
        producerThread2.start();
        producerThread3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();

    }
}