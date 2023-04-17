// ref: 
// https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html
// https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
// https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html
public class Deadlock {
    static class Friend {
        private final String name;
        
        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s"
                + "  has bowed to me!%n", 
                this.name, bower.getName());
            bower.bowBack(this);
        }

        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s"
                + " has bowed back to me!%n",
                this.name, bower.getName());
        }
    }

    public static void main(String[] args) throws Exception{
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(new Runnable() {
            public void run() { alphonse.bow(gaston); }
        }).start();
        // Thread.sleep(10000);
        new Thread(new Runnable() {
            public void run() { gaston.bow(alphonse); }
        }).start();
    }
}