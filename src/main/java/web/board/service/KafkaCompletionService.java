package web.board.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class KafkaCompletionService {

    private final CountDownLatch latch = new CountDownLatch(1);  // 메시지 소비가 끝날 때까지 대기

    // 메시지 소비 완료 신호를 받으면 latch를 감소시킴
    public void messageConsumed() {
        latch.countDown();
    }

    // 메시지 소비 완료까지 대기
    public void waitForCompletion() throws InterruptedException {
        latch.await();  // 소비 완료될 때까지 대기
    }
}
