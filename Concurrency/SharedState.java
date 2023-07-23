import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedState {
    // shared resource
    private volatile int data;
    // lock for the resource
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private static final int READ_CYCLES = (int)1e8;
    private static final int WRITE_CYCLES = (int)1e8;

    public SharedState(int initialData) {
        this.data = initialData;
    }

    /**
     * Retrieves the data from a private static variable.
     * Representing a shared resource
     *
     * @return The data value stored
     */
    private int getData() {
        rwLock.readLock().lock();
        try {
            return data;
        } finally {
            rwLock.readLock().unlock();
        }
    }

   /**
    * Updates the value of the private static variable 'data'.
    */
    private void updateData() {
        rwLock.writeLock().lock();
        try {
            data += 1;
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    public static void main(String ...args) throws InterruptedException {
        final long startTime = System.nanoTime();
        SharedState sharedState = new SharedState(0);
        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int cycles = 0; cycles < READ_CYCLES; cycles++) {
                    int value = sharedState.getData();
                    // to keep I/O low to influence perf
                    if(cycles % (READ_CYCLES/10) == 0){
                        System.out.println("read: " + value);
                        System.out.flush();
                    }
                }
            }
        });
        Thread writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int cycles = 0; cycles < WRITE_CYCLES; cycles++) {
                    sharedState.updateData();
                    int value = sharedState.getData();
                    if(cycles % (WRITE_CYCLES/10) == 0){
                        System.out.println("post write: " + value);
                        System.out.flush();
                    }
                }
            }
        });
        readerThread.start();
        writerThread.start();
        readerThread.join();
        writerThread.join();
        final long duration = System.nanoTime() - startTime;
        System.out.println("time taken(ns): " + duration);
    }
}
