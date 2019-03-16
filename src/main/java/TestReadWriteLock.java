import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述什么是读写锁ReadWriteLock
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteDemo rw=new ReadWriteDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    rw.set((int) (Math.random()*101));
                }
            }
        },"set").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    rw.get();
                }
            }
        },"write").start();
    }

}
class ReadWriteDemo {
    private int number= 0;
    //启动同步锁
    ReadWriteLock lock = new ReentrantReadWriteLock();
    public void get(){
        //开启读锁
        lock.readLock().lock();
        try {
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName()+":"+number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭读锁
            lock.readLock().unlock();
        }

    }
    public void set(int number){
        //开启写锁
        lock.writeLock().lock();
        try {
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }

    }
}