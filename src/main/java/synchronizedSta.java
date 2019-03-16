/**
 * 演示什么是同步块synchronized
 */
public class synchronizedSta {
    public static void main(String[] args){
        Account acct=new Account("1234567",1000);
        new DrawThread("甲",acct,800).start();
        new DrawThread("乙",acct,800).start();
    }
}
class Account {
    private String accountNo;
    private double balance;
    public Account(String accountNo,double balance){
        this.accountNo=accountNo;
        this.balance=balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return accountNo.equals(account.accountNo);

    }

    @Override
    public int hashCode() {
        return accountNo.hashCode();
    }
}

class DrawThread extends Thread {
    private Account account;
    private double drawAmount;

    public DrawThread(String name, Account account, double drawAmount) {
        super(name);
        this.account = account;
        //取钱的数量
        this.drawAmount = drawAmount;
    }
    public void run(){
        synchronized (account){
            if(account.getBalance()>=drawAmount){
                System.out.println(getName() + "取钱成功，吐出钞票： " + drawAmount);
                try{
                    Thread.sleep(1);
                }catch(InterruptedException ex){
                    ex.getStackTrace();
                }
                account.setBalance(account.getBalance()-drawAmount);
                System.out.println("\t余额为："+account.getBalance());
            }else{
                System.out.println(getName()+"取钱失败，余额不足");
            }
        }
    }
}
