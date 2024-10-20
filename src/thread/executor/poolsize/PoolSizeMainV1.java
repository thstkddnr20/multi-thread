package thread.executor.poolsize;

import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * ExecutorService가 쓰레드와 풀을 어떻게 관리하는지 알려준다
 * corePoolSize와 maximumPoolsize의 차이점을 알 수 있다
 * corePoolSize가 가득차면 queue에 저장하고 queue도 가득차면 maximumPoolSize의 수만큼 쓰레드가 늘어난다
 * 그 후 keepAliveTime의 시간만큼 기다린 후 corePoolSize를 초과해서 만들어진 maximumPoolSize의 수가 줄어든다
 * (여기서는 4 -> 2로 줄어들게 된다)
 * 또한 maximumPoolSize, queue가 한도에 도달했을 때 새로운 task가 들어오면 RejectedExecutionException이 발생한다
 */
public class PoolSizeMainV1 {
    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ExecutorService es = new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1"));
        printState(es, "task1");

        es.execute(new RunnableTask("task2"));
        printState(es, "task2");

        es.execute(new RunnableTask("task3"));
        printState(es, "task3");

        es.execute(new RunnableTask("task4"));
        printState(es, "task4");

        es.execute(new RunnableTask("task5"));
        printState(es, "task5");

        es.execute(new RunnableTask("task6"));
        printState(es, "task6");

        try {
            es.execute(new RunnableTask("task7"));
            printState(es, "task7");
        } catch (RejectedExecutionException e) {
            log("task7 실행 거절 예외 발생: " + e);

        }

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과 ==");
        printState(es);

        es.close();
        log("== shutdown 완료 ==");
        printState(es);
    }
}
