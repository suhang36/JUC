import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 创建三个线程，依次打印ABC10次
 */
public class TreTesr {
    public static void main(String[] args) {
        AlternateDemo demo = new AlternateDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    demo.toopA(i);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    demo.toopB(i);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    demo.toopC(i);
                }
            }
        }).start();
    }
}
class AlternateDemo{
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void toopA(int tatal){
        lock.lock();
        try{
            if (number != 1){
                    condition1.await();
            }
            System.out.println("A"+"   "+tatal);
            number=2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void toopB(int tatal){
        lock.lock();
        try{
            if (number !=2){
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("B"+"   "+tatal);
            number=3;
            condition3.signal();
        } finally {
            lock.unlock();
        }
    }
    public void toopC(int tatal){
        lock.lock();
        try{
            if (number !=3){
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C"+"   "+tatal);
            number=1;
            condition1.signal();
            System.out.println("------");
        } finally {
            lock.unlock();
        }
    }
}
