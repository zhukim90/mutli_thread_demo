package org.example.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {
    public static void main(String[] args) {
        // 创建2000个随机数的数组
        long[] arr = new long[2000];
        long expectedSum = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random();
            expectedSum += arr[i];
        }
        System.out.println("ExpectedSum = " + expectedSum);

        ForkJoinTask<Long> task = new SumTask(arr, 0, arr.length);

        long startTime = System.currentTimeMillis();
        long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("Fork/Join sum:" + result + " in " + (endTime- startTime) + "ms");
    }

    static Random random = new Random();

    static long random(){
        return random.nextInt(10000);
    }
}

class SumTask extends RecursiveTask<Long>{

    static final int THRESHOLD = 500;
    long[] arr;
    int start;
    int end;

    public SumTask(long[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if(end - start < THRESHOLD){
            // 如果任务足够小，直接计算
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += arr[i];
                // 故意放慢计算速度
                try {
                    Thread.sleep(1);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
            return sum;
        }else{
            // 任务太大,一分为二:
            int middle = (end + start) /2;
            System.out.println(String.format("split %d~%d==>%d~%d %d~%d", start, end, start, middle, middle, end));
            SumTask sumTask1 = new SumTask(this.arr, start, middle);
            SumTask sumTask2 = new SumTask(this.arr, middle, end);

            invokeAll(sumTask1, sumTask2);
            long subResult1 = sumTask1.join();
            long subResult2 = sumTask2.join();
            long result = subResult1 + subResult2;
            System.out.println("result= " + subResult1 + "+" + subResult2 + "==>" + result);
            return result;

        }
    }
}
