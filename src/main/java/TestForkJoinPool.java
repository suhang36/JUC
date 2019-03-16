import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 将线程拆分成多个线程，用工作窃取的方式来计算一个求和
 */
public class TestForkJoinPool {
    //开始计算
    public static void main(String[] args) {
        Instant start = Instant.now();
        //创建一个支持的这个框架的类ForkPool
        ForkJoinPool pool = new ForkJoinPool();
        //创建一个框架类，用多态的方式，用RecursiveTask的上层接口ForkJoinTask建立这个类
        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L,1000000000L);
        //将框架放入支持类中
        Long sum=pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        //记录完成运算的时间
        System.out.println("完成运算的时间为"+ Duration.between(start,end).toMillis());//1424
    }

    /**
     * 用传统的方法计算累加和
     */
    @Test
    public void test1(){
        Instant start = Instant.now();
        Long sum =0L;
        for (Long i = 0L; i <= 100000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费的时间为"+Duration.between(start,end).toMillis());//1913
    }
    /**
     * 用jdk1.8的方法来运算累加
     */
    @Test
    public void test2(){
        Instant start = Instant.now();
        Long sum=LongStream.rangeClosed(0L,1000000000L)//带结尾的计算
                            .parallel()
                            .reduce(0l,Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("所用的时间为"+Duration.between(start,end).toMillis());//1484
    }

}
//创建一个RecursiveTask，递归的方式来拆分
class ForkJoinSumCalculate extends RecursiveTask<Long>{
    //创建几个长整型的变量来存储开始，结束，和临界值
    private long start ;
    private long end;
    //临界值
    private static final long THURSHOLD = 10000L;
    public ForkJoinSumCalculate(long start,long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        //当拆分到临界值就不拆了，计算总和
        if(length<=THURSHOLD) {
            long sum = 0;
            for (long i = start; i <=end ; i++) {
                sum +=i;
            }
            return sum;
        }else {
            //获取中间值
            long middle = (start+end) / 2;
            /**
             * 递归开始：先创建一个这个类的对象计算前半部分，调用。fork();拆封
             *          再拆分右半部分
             */
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();
            /**
             * 当拆分到不能再拆分了就开始技算和
             * 再返回技术的值return sum;
             * 返回到上层的对象后再通过left.join()+right.join();将左右子任务的返回值的值再相加，最后相加到最上层对象 返回最终值
             */
            return left.join()+right.join();

        }

    }
}
