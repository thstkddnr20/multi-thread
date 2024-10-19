package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureCancelMain {

//    private static boolean mayInterruptIfRunning = true;
    private static boolean mayInterruptIfRunning = false;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        log("Future.state: " + future.state());

        //일정 시간 후 취소 시도
        sleep(3000);

        //cancel() 호출
        log("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(mayInterruptIfRunning); //future을 취소 상태로 변경한다 true를 줄경우 실행중이던 작업에 interrupt를 주어 작업 중단, false일 경우 계속 진행 but future.get()으로 값을 받으려고 할 경우 CancellationException 발생
        log("cancel(" + mayInterruptIfRunning + ") result: " + cancelResult);

        //결과 확인
        try {
            log("Future result: " + future.get());
        } catch (CancellationException e) {
            log("Future은 이미 취소되었습니다");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    static class MyTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            try {
                for (int i = 0; i < 10; i++) {
                    log("작업 중: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log("인터럽트 발생");
                return "Interrupted";
            }

            return "Completed";

        }
    }
}
