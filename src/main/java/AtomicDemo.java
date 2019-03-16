
public class AtomicDemo implements Runnable {
    private  int i = 0;

    public int getI() {
        return i++;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+":"+getI());
    }
}
