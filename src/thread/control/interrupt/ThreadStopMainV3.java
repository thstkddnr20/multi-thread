package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV3 {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(100);
        log("작업 중단 지시 thread.interrupt");
        thread.interrupt();
        log("work 쓰레드 인터럽트 상태1 = " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                log("작업 중");
            }
            log("work 쓰레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());
            //인터럽트 상태가 터졌음에도 true로 유지됨
            //우리가 원하는 것은 한번만 interrupt가 터져 다시 상태를 정상으로 돌리는 것
            log("state = " + Thread.currentThread().getState());

            try {
                log("자원 정리");
                Thread.sleep(1000); // interrupt 상태가 true로 지속되어 catch문으로 넘어가게됨
                log("작업 종료");
            } catch (InterruptedException e) {
                log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
                log("work 쓰레드 인터럽트 상태3 = " + Thread.currentThread().isInterrupted()); //catch문에 들어와서야 interrupt 상태가 false로 바뀐다
            }
            log("작업 종료");
        }
    }
}
