package org.example.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) {

        // 1
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//        MyThread thread = new MyThread();
//        for (int i = 0; i < 10; i++) {
//            pool.submit(thread);
//        }
//        pool.shutdown();
//        System.out.println("result1= " + thread.getCount());


        // 2
        ExecutorService pool2 = Executors.newFixedThreadPool(10);
        MyThread2 thread2 = new MyThread2();
        for (int i = 0; i < 10; i++) {
            pool2.submit(thread2);
        }
        pool2.shutdown();
        System.out.println("result2 = " + thread2.getCount());
    }
}

/**
 * 不适用原子性的线程
 */
class MyThread implements Runnable{

    private volatile int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            addCount();
        }
    }

    private void addCount(){
        count++;
    }

    public int getCount(){
        return count;
    }
}

/**
 * 使用原子性的线程
 */
class MyThread2 implements Runnable{
    private AtomicInteger count2 = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            // 自增
            count2.getAndIncrement();
        }
    }

    public int getCount(){
        return count2.get();
    }
}
