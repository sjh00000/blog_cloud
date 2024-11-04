package com.example.gatewayservice;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GatewayServiceApplicationTests {
    static volatile int  num =0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                num++;
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                num++;
            }
        });
        t2.start();
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(num);
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换 arr[j] 和 arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
