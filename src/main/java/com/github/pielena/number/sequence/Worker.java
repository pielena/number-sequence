package com.github.pielena.number.sequence;

import java.util.concurrent.locks.Lock;

public class Worker implements Runnable {

    private final int threadNumber;
    private final Lock lock;
    private final ConditionService conditionService;

    public Worker(int threadNumber, Lock lock, ConditionService conditionService) {
        this.threadNumber = threadNumber;
        this.conditionService = conditionService;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 1; i <= 10; i++) {
                lock.lock();
                try {
                    conditionService.manageCondition(threadNumber);
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            for (int i = 9; i > 1; i--) {
                lock.lock();
                try {
                    conditionService.manageCondition(threadNumber);
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
        Thread.currentThread().interrupt();
    }
}
