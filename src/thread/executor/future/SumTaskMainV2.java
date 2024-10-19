package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;

public class SumTaskMainV2 {
    //Future<Integer> future1 = es.submit(task1); 를 하여 task1의 수행을 지시
    //Integer sum1 = future1.get(); 에 도착했을 때 task1의 반환값이 오기까지 대기한다

    //Future가 없다고 가정했을 때
    //Integer sum1 = es.submit(task1);과 같은 코드를 실행시키면 여기서 실행하자마자 블로킹을 하여 sum1에 값이 들어와야지 다음 지시를 내릴 수 있으므로 단일 쓰레드를 사용하는 것과 같다


    /**
     * 올바른 사용
     * Future<Integer> future1 = es.submit(task1);
     * Future<Integer> future2 = es.submit(task2);
     * Integer sum1 = future1.get();
     * Integer sum2 = future2.get();
     *
     * 잘못된 사용
     * Future<Integer> future1 = es.submit(task1);
     * Integer sum1 = future1.get();
     * Future<Integer> future2 = es.submit(task2);
     * Integer sum2 = future2.get();
     *
     */

    //결론 : submit으로 원하는 작업을 다 던지고 get()을 후반에 사용하여 값을 받아 사용한다
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = es.submit(task1); //새로운 쓰레드에게 할일을 주는 곳
        Future<Integer> future2 = es.submit(task2);

        Integer sum1 = future1.get(); // 요청 메서드가 블락됨 -> Thread.join()과 같은 결이다 (블럭 메서드)
        Integer sum2 = future2.get();

        log("task1.result=" + sum1);
        log("task2.result=" + sum2);

        int sumAll = sum1 + sum2;
        log("task1 + task2 = " + sumAll);
        log("End");
    }

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws Exception {
            log("작업 시작");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            log("작업 완료 result =" + sum);
            return sum;
        }
    }
}
