/**
 * 什么是线程8锁
 * 1.两个普通的线程方法，标准打印？ one two
 * 2.新增一个sleep()给getone() one two
 * 3.新增加一个没上锁的方法getThree() three one two
 * 4.修改getone()方法为静态方法 two one
 * 5.两个对象，两个普通方法两个打印值two one
 * 6.两个均为静态方法 两个对象 one two
 * 7.一个静态一个不是静态，两个对象 two one
 * 8.两个均为静态方法 一个对象 one two
 *线程八锁的关键：
 * 非静态方法的锁默认是 this 静态方法的实例对应的是class
 * 在同一时刻，锁只能由一个线程持有，无论几个方法
 * 个人理解
 * 静态方法的锁是这个类的class 只有一个，而非静态方法创建了几个对象就有几把锁，这些锁是独立的。
 * 调用静态方法就用的是class的锁，调用非静态方法就用的对象this 的锁，这些都是独立的，
 * 哪怕在一个类有静态方法，也有非静态方法
 * 都不影响对锁的竞争
 * 对于没有加锁的方法，由于没有锁的需要，当程序执行时，就自己执行了。
 */
public class TestTread8Monitor {
    public static void main(String[] args) {
        Tread8Monitor thread1 = new Tread8Monitor();
        Tread8Monitor thread2 = new Tread8Monitor();
        new Thread(new Runnable() {
            @Override
            public void run() {
                thread1.loopOne();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                thread2.loopTwo();
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                thread1.loopThree();
//            }
//        }).start();
    }
}
class Tread8Monitor{
    private int number=0;
    public static   synchronized void loopOne(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }
    public static synchronized void loopTwo(){
        System.out.println("Two");
    }
    public void loopThree(){
        System.out.println("Three");
    }
}