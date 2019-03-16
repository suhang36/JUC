/**
 * 演示什么是同步方法synchronized
 */
public class synchronizedCla {
    public static void main(String[] args) {
        //两个不同的对象
        HasSelfPrivateNum has1=new HasSelfPrivateNum();
        NoSysnTest noSysnTest=new NoSysnTest();
        //两个线程
        CountOperate c1=new CountOperate(has1,noSysnTest);
        c1.setName("A");
        CountOperate c2=new CountOperate(has1,noSysnTest);
        c2.setName("B");
        //启动之后，结果是异步执行，与启动顺序无关
        c1.start();
        c2.start();
        noSysnTest.play();

    }
}
class NoSysnTest {
    public  void play(){
        System.out.println("sleep begin threadName="+Thread.currentThread().getName()+" time="+System.currentTimeMillis());
        try{
            Thread.sleep(5000);
            System.out.println("sleep end threadName="+Thread.currentThread().getName()+" time="+System.currentTimeMillis());

        }catch (InterruptedException e){

        }

    }
}
class CountOperate extends Thread {
    private HasSelfPrivateNum numRef;
    private NoSysnTest noSysnTest;
    public CountOperate(HasSelfPrivateNum numRef,NoSysnTest noSysnTest){
        this.numRef=numRef;
        this.noSysnTest=noSysnTest;
    }
    public void run(){
        //调用对象的同步方法
        numRef.addI("a",noSysnTest);
    }
}
class HasSelfPrivateNum {

    public synchronized void addI(String userName,NoSysnTest noSysnTest){
        //调用非同步的方法
        noSysnTest.play();
    }

}
