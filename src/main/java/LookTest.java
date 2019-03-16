import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 买票方法：演示如何让多个线程读写同一个数据，保证线程安全
 * 保证线程安全的方法：Lock同步锁
 */
public class LookTest {
    public static void main(String[] args) {
        Looks looks = new Looks();
        new Thread(looks,"1号窗口").start();
        new Thread(looks,"2号窗口").start();
        new Thread(looks,"3号窗口").start();
    }
}
class Looks implements Runnable{
    //初始化100张票
    private int tickes = 100;
    //创建同步锁,由实现类ReentrantLock创建实现
    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        //上锁
        while (true){
        lock.lock();
        try {
                if (tickes>0){
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName()+"完成售票，余票为"+--tickes);
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    }
}
