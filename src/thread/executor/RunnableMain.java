package thread.executor;

import java.util.Random;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class RunnableMain { //Runnable의 불편함
    public static void main(String[] args) throws InterruptedException {
        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task, "Thread-1");
        thread.start();
        thread.join();

        int result = task.value; //task.value로 따로 값을 꺼내야해서 불편
        log("result value = " + result);
    }

    static class MyRunnable implements Runnable {

        int value; //value라는 값을 구현하고 그 안에 값을 저장

        @Override
        public void run() {
            log("Runnable 시작");
            sleep(2000);
            value = new Random().nextInt(10);
            log("create value = " + value);
            log("Runnable 완료");
        }
    }
}
