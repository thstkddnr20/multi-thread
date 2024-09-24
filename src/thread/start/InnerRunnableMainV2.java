package thread.start;

import static util.MyLogger.log;

public class InnerRunnableMainV2 {
    public static void main(String[] args) {
        log("main() start");


        new Thread(new Runnable() {
            @Override
            public void run() {
                log("run()");
            }
        }).start();

        log("main() end");
    }
}
