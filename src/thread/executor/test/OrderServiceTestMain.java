package thread.executor.test;

import java.util.concurrent.ExecutionException;

public class OrderServiceTestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String orderNo = "Order#1234";  // 예시 주문 번호
        OrderService orderService = new OrderService();
        orderService.order(orderNo);
    }
}
