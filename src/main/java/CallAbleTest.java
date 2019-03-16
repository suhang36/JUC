import java.util.concurrent.*;

/**
 * callable是一种启动线程的方式
 * 	1.
 *     相较于Rounable 这个方式有返回值并且有抛出的异常
 * 	2.
 * 需要FutureTask 实现类的支持，用于接收运算结果（是future的实现类）
 * 	3.
 * FutureTask也可用于闭锁的操作
 */
public class CallAbleTest {
    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        //执行callabe的方式必须要futuretask 类实现支持
       FutureTask<Integer> re = new FutureTask<Integer>(demo);
//       启动线程
       new Thread(re).start();
       //获取结果
        try {
            int sum = re.get();
            System.out.println("sum = " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
//实现callable接口
class ThreadDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum= 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}
