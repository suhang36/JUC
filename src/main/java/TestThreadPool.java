import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一、线程池：提供一个线程队列，保存着所有等待的线程，避免了线程的创建和销毁所带来的资源的浪费
 * 二、线程池的体系结构
 *   java.until.conncurrent.Executor 负责线程创建和调度的根接口
 *      -ExecutorService  子接口，线程池的主要接口
 *          -ThreadPoolExecutor     线程池的实现类
 *          -ScheduledExecutorService 子接口,负责线程的调度
 *              -ScheduledThreadPoolService 继承ThreadPoolExecutor，实现ScheduledExecutorService
 * java jdk5.0 后提供了一套工具类
 * 三、工具类Executors
 *      -ExecutorService newFixedThreadPool 创建固定大小的线程池
 *      -ExecutorService newCachedThreadPool 缓存线程池，数量不固定，根据需求自动创建
 *      -ExecutorService newSingleThreadPool 单个线程池
 * SchedulExecutorService new  ScheduledThradPoolService 创建固定大小的线程池，可以延迟执行
 *
 */

public class TestThreadPool {
    public static void main(String[] args) {
        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.submit(threadPoolDemo);
        }
        pool.shutdown();
    }
//    new Thread(threadPoolDemo,"thread1").start();
//    new Thread(threadPoolDemo,"thread2").start();
}
class ThreadPoolDemo implements Runnable{
        private  int number=0;
    @Override
    public void run() {
        for (int i =1 ; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName()+":"+ ++number);
        }
    }
}
