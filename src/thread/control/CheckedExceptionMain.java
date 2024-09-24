package thread.control;

import static util.ThreadUtils.sleep;

public class CheckedExceptionMain {

    static class CheckedRunnable implements Runnable {
        @Override
        public void run() {
            sleep(1000);
        }
    }
}
