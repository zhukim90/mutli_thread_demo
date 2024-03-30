package org.example;

public class Domo02_Interrupt {

    public static void main(String[] args) {
        MyThread02 myThread02 = new MyThread02();
        myThread02.start();
        try {
            Thread.sleep(1000); // 主线程休眠1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThread02.interrupt();
    }
}


class MyThread02 extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + "正在执行--" + Thread.currentThread().isInterrupted());
        }
        System.out.println(Thread.currentThread().getName() + "Done--" + Thread.currentThread().isInterrupted());
    }
}
