import java.sql.Time;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 这个类是测试ScheduledThreadPool工具类的线程调度的，和TestThreadPool.java是一样的
 */

public class TestScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建一个线程池，固定线程数量5
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 5; i++) {
           Future<Integer> future= pool.schedule(new Callable<Integer>() {//创建一个线程
                @Override
                public Integer call() throws Exception {
                    int number  = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName()+":"+number);
                    return number;
                }
            },1, TimeUnit.SECONDS);//延迟三秒
            System.out.println(future.get());
        }
        //关闭连接池

    }
}
