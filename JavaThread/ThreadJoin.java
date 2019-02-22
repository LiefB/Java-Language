import java.util.ArrayList;
import java.util.List;

public class ThreadJoin{

    public static void main(String[] args) throws InterruptedException {
        /**
         * 所有子线程start之后，通过join方法等待子线程执行完成，当所有子线程执行完成后再执行主线程后面的代码。
         */
        int threadNum = 10;
        List<Thread> workers = new ArrayList<>();

        for (int i = 0; i < threadNum; i++) {
            Thread worker = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0 ; i < 5; i++){
                        System.out.println("执行子线程：" + Thread.currentThread().getName());
                    }
                }
            });
            workers.add(worker);
        }

        for (int i = 0; i < threadNum; i++) {
            workers.get(i).start();
        }

        for (int i = 0; i < threadNum; i++) {
            workers.get(i).join();
        }

        System.out.println("所有子线程执行完毕，继续返回执行主线程...");

    }

}