import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决虚假唤醒机制：一个线程可能会唤醒其他的线程同时进行操作，但我们希望只唤醒其中一个线程参与操作
 * 根据api的说法需要将等待的判断条件写在while循环中，不写在if中
 * 这样当程序继续运行时可以再判断一次，防止虚假唤醒
 *
 * 用 Lock 同步锁的方式来解决
 */

class TestXJHXForLock {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor,"生产者A").start();
        new Thread(consumer,"消费者B").start();
        new Thread(productor,"生产者C").start();
        new Thread(consumer,"消费者D").start();
    }
}

class Clerk{
    //商品数
    private int product = 0;
    //启动同步锁
    private Lock lock = new ReentrantLock();
    //启动condition线程通信类
    private  Condition condition=lock.newCondition();
    //进货
    public void get(){
        //加锁
        lock.lock();
        try {
            while(product >=1){
                System.out.println("产品已满！");
                try {
                    //等待
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+":"+ ++product);
            //启动等待
            condition.signalAll();
        }finally {
            //释放锁
            lock.unlock();
        }
    }
    //卖货
    public void sale(){
        //加锁
        lock.lock();
        try {
            while (product<=0){
                System.out.println("缺货");
                try {
                    //等待
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+":"+ --product);
            //唤醒
            condition.signalAll();
        }finally {
            //解锁
            lock.unlock();
        }
    }
}

//生产者
class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk){
        this.clerk=clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i <20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

//消费者
class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk=clerk;
    }
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.sale();
        }
    }
}