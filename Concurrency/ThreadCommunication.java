import java.lang.Runnable;
import java.lang.Thread;
import java.util.LinkedList;

class SharedQueue{
    private static LinkedList<Integer> queue;
    private Object queueLock;

    public SharedQueue(){
        queue = new LinkedList<>();
    }
    
    public void addElement(int element){
        synchronized(queueLock){
            queue.addLast(element);
        }
    }

    public int getElement(){
        int result;
        synchronized(queueLock){
            result = queue.getLast();
        }
        return result;
    }
}

class WriterTask implements Runnable{
    private String taskName;
    private static SharedQueue sharedQueue;
    private int startIndex;

    public WriterTask(String taskName, SharedQueue sharedQueue){
        this.taskName = taskName;
        this.sharedQueue = sharedQueue;
        this.startIndex = 1;
    }

    public WriterTask(String taskName, SharedQueue sharedQueue, int startIndex){
        this.taskName = taskName;
        this.sharedQueue = sharedQueue;
        this.startIndex = startIndex;
    }

    @Override
    public void run() {
        for(int job = this.startIndex; job <= this.startIndex +  100; job++){
            try{
                System.out.println("" + this.taskName + " doing job " + job);
                sharedQueue.addElement(job);
                System.out.flush();
                Thread.sleep(1000);
            }catch(Exception exception){
                System.err.println("" + this.taskName + " error in doing job " +
                    job + " details: "+exception.getMessage());
            }
        }
    }
}

class ReaderTask implements Runnable{
    private String taskName;
    private static SharedQueue sharedQueue;

    public ReaderTask(String taskName, SharedQueue sharedQueue){
        this.taskName = taskName;
        this.sharedQueue = sharedQueue;
        // this.startIndex = 1;
    }

    @Override
    public void run() {
        for(int job = 1; job <= 100; job++){
            try{
                int result = sharedQueue.getElement();
                System.out.println("" + this.taskName + " iteration: " + job +" result: "+result);
                System.out.flush();
                Thread.sleep(3000);
            }catch(Exception exception){
                System.err.println("" + this.taskName + " error in doing job " + job);
            }
        }
    }
}

class ThreadCommunication{
    public static void main(String[] args) throws Exception{
        SharedQueue sharedQueue = new SharedQueue();

        WriterTask taskA = new WriterTask("Writer task W1", sharedQueue);
        Thread firstWriterThread = new Thread(taskA);

        WriterTask taskB = new WriterTask("Writer task W2", sharedQueue, 101);
        Thread secondWriterThread = new Thread(taskB);

        firstWriterThread.start();
        secondWriterThread.start();

        ReaderTask readerTask = new ReaderTask("reader task R1 ", sharedQueue);
        Thread readerThread = new Thread(readerTask);
        readerThread.start();

        readerThread.join();
        firstWriterThread.join();
        secondWriterThread.join();

    }
}