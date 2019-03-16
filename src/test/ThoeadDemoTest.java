import org.junit.Test;

public class ThoeadDemoTest {
    @Test
    public void testTh(){
        ThoeadDemo thoeadDemo = new ThoeadDemo();

        new Thread(thoeadDemo).start();

        while(true){

            if(thoeadDemo.isFlag()==true){
                System.out.println("------");
                break;
            }

        }
    }
    @Test
    public void testI(){
        AtomicDemo atomicDemo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(atomicDemo).start();
        }
    }
}