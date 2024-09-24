package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;

public class MyPrinterV4 {
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

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (jobQueue.isEmpty()) {
                    Thread.yield(); //양보하여 다른 스레드가 작업을 하도록 함
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
