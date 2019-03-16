import java.util.concurrent.Callable;

public class ThoeadDemo implements Runnable{
    private volatile boolean flag= false;
    @Override
    public void run() {

        flag =true;
        System.out.println("flag = " + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
