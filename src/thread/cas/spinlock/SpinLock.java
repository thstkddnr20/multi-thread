package thread.cas.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLock {

    private final AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        log("락 획득 시도");
        while (!lock.compareAndSet(false, true)) {
            //락이 false라면 true로 변경한다 그 후 true를 반환한다 앞에 !를 붙였으므로 while문에서 빠져나감
            //락이 true라면 false 반환, !가 붙었으므로 true -> while문에 갇힌다
            //원자적이지 않은 연산을 원자적으로 바꿈 -> CPU 차원에서 1개로 묶임
            log("락 획득 실패 - 스핀 대기");
        }
        log("락 획득 완료");
    }

    public void unlock() {
        lock.set(false);
        log("락 반납 완료");
    }
}
