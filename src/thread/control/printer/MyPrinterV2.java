package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class MyPrinterV2 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner userInput = new Scanner(System.in);
        while (true) {
            log("프린터 할 문서를 입력하세요. 종료(q): ");
            String input = userInput.nextLine();
            if (input.equals("q")) {
                printerThread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {

        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        /**
         * while true 대신 !Thread.interrupted를 사용한 이유
         * while true문 부분일 때 interrupt가 걸릴 시 바로 멈추지 않고 Thread.sleep 부분까지 가서 TIMED_WAITING이 될때
         * 멈추게 되어 반응성이 살짝 느리다
         */
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (jobQueue.isEmpty()) {
                    continue;
                }
                try {
                    String job = jobQueue.poll();
                    log("출력 시작: " + job + "대기 문서: " + jobQueue);
                    Thread.sleep(3000);
                    log(job + " 출력 완료");
                } catch (InterruptedException e) {
                    log("프린터 강제 종료");
                    break;
                }

            }
            log("프린터 종료");
        }

        public void addJob(String job) {
            jobQueue.offer(job);
        }
    }
}
