package cn.nj.demo2;

/**
 * @author ZTY
 * @classname TestSynchronized
 * @description TODO
 * @date 2020/11/1910:27
 */
public class TestSynchronized {

    private static volatile  int count =0;

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<10;i++){
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            });
            thread.start();
        }
        // 等10个线程运行完毕
        Thread.sleep(1000);
        System.out.println(count);
    }

    private static  void add(){
        synchronized (TestSynchronized.class){
            count++;
        }

    }

}
