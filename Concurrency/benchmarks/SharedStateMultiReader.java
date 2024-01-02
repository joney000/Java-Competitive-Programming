import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedStateMultiReader {
    // shared resource
    private volatile int data;
    // lock for the resource
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private static final int READ_CYCLES = (int)1e6;
    private static final int WRITE_CYCLES = (int)1e2;
    private static final int READER_THREADS = 100; // read heavy systems
    private static final int WRITER_THREADS = 10;

    public SharedStateMultiReader(int initialData) {
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
        int value = 0;
        try {
            value = data;
        } finally {
            rwLock.readLock().unlock();
        }
        return value;
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
        SharedStateMultiReader sharedState = new SharedStateMultiReader(0);
        Thread []readers = new Thread[READER_THREADS];
        for(int readerId = 0; readerId < READER_THREADS; readerId++){
            final int threadId = readerId;
            readers[threadId] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int cycles = 0; cycles < READ_CYCLES; cycles++) {
                        int value = sharedState.getData();
                        // to keep I/O low to influence perf
                        if(cycles % (READ_CYCLES/10) == 0){
                            // System.out.format("read threadId: %d read_value: %d\n", threadId, value);
                            // System.out.flush();
                        }
                    }
                    System.out.format("\nread threadId: %d finished", threadId);
                    System.out.flush();
                }
            });
            readers[threadId].start();
        }
        Thread []writers = new Thread[WRITER_THREADS];
        for(int writerId = 0; writerId < WRITER_THREADS; writerId++){
            final int threadId = writerId;
            writers[threadId] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int cycles = 0; cycles < WRITE_CYCLES; cycles++) {
                        sharedState.updateData();
                        if(cycles % (WRITE_CYCLES/10) == 0){
                            // int value = sharedState.getData();
                            // System.out.format("write threadId: %d write_value: %d\n", threadId, value);
                            // System.out.flush();
                        }
                    }
                    System.out.format("\nwrite threadId: %d finished", threadId);
                    System.out.flush();
                }
            });
            writers[threadId].start();
        }

        for(int readerId = 0; readerId < READER_THREADS; readerId++){
            readers[readerId].join();
        }
        for(int writerId = 0; writerId < WRITER_THREADS; writerId++){
            writers[writerId].join();
        }
        final long duration = System.nanoTime() - startTime;
        System.out.println("SharedStateMultiReader time taken(sec): " + duration/(1e9));
    }
}
