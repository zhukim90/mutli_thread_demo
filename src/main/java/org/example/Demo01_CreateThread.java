package org.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程创建方式
 */
public class Demo01_CreateThread {

    public static void main(String[] args){
        // 1
        MyThread myThread = new MyThread();
        myThread.start();

        // 2
        Thread myThread1 = new Thread(new MyRunnable());
        myThread1.start();

        // 3
        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<>(myCallable);

        Thread myThread2 = new Thread(futureTask);
        myThread2.start();

        String result = null;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);

    }
}

/**
 * 继承Thread
 */
class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"我是继承Thread来定义线程");
    }
}

/**
 * 实现Runnable接口
 */
class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"我是实现Runnable来定义线程");
    }
}

/**
 * 实现Callable接口
 */
class MyCallable implements Callable<String>{

    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName() + "我是实现Callable接口来定义线程";
    }
}