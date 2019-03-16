import sun.font.FontRunIterator;

import javax.sql.rowset.serial.SQLOutputImpl;
import java.util.concurrent.CountDownLatch;

/**
 * 该方法的作用：演示什么是闭锁,让main线程等待分线程全部执行完成再执行
 * 用的方法：CountDownLatch
 *
 */
public class TestBiShuo  {
    //定义一个闭锁
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(5);//5表示参与闭锁的数量
        //定义一个线程类并将闭锁传入
        CountDw dw = new CountDw(latch);
        //获取线程开始时间
        long start = System.currentTimeMillis();
        for(int i=0 ; i<5 ;i++){
            new Thread(dw).start();
        }

        try {
            //开启主线程线程等待
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            long time = System.currentTimeMillis() - start;
        System.out.println(time);
    }

}

class CountDw implements Runnable{
//    闭锁
    private CountDownLatch latch;
    public CountDw(CountDownLatch latch){
        this.latch = latch;
    }
    @Override
    public void run() {
        //可能出现的线程安全
        synchronized (this) {
            //无论该线程是否出现问题都算一个线程
            try {
                for (int i = 0; i < 10000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            }finally {
                //减少一个闭锁线程
                latch.countDown();
            }

        }
    }
}
