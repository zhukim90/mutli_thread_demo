package org.example.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主线程等待子线程执行完成后执行
 */
public class CountDownLatch01 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        final CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "执行完成");
                    latch.countDown(); // 计数器减1

                }
            };

            service.execute(runnable);
        }

        System.out.println(Thread.currentThread().getName() + "等待子线程执行完成后在执行");

        try {
            latch.await(); // 阻塞当前线程， 直到计数器的值为0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "执行完成");

        service.shutdown();
    }
}

