package thread.collection.simple.list;

import static util.MyLogger.log;

public class SimpleListMainV2 {
    public static void main(String[] args) throws InterruptedException {
//        test(new BasicList());
//        test(new SyncList());
        test(new SyncProxyList(new BasicList())); // synchronized가 붙은 프록시 클래스로 한번 감싸서 BasicList()를 수행
    }

    private static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());

        //A를 리스트에 저장하는 코드
        Runnable addA = new Runnable() {
            @Override
            public void run() {
                list.add("A");
                log("Thread-1: list.add(A)");
            }
        };

        //B를 리스트에 저장하는 코드
        Runnable addB = new Runnable() {
            @Override
            public void run() {
                list.add("B");
                log("Thread-2: list.add(B)");
            }
        };

        Thread thread1 = new Thread(addA, "Thread-1");
        Thread thread2 = new Thread(addB, "Thread-2");
        thread1.start(); // thread1: 배열의 0번에 A가 들어감 -> thread2: 배열의 0번의 A를 B로 바꿈 -> 결과: [B, null] but, 결과는 달라질 수 있음
        thread2.start();
        thread1.join();
        thread2.join();
        log(list);
    }
}
