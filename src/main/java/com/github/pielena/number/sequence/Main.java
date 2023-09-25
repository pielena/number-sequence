package com.github.pielena.number.sequence;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        startHomeTask(2);
    }

    public static void startHomeTask(int threadCount) {
        Lock lock = new ReentrantLock();
        ConditionService conditionService = new ConditionService(threadCount, lock);

        for (int i = 0; i < threadCount; i++) {
            new Thread(new Worker(i, lock, conditionService)).start();
        }
    }
}
