package com.github.pielena.number.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionService {

    private int requiredThreadNumber = 0;
    private final List<Condition> conditions = new ArrayList<>();

    public ConditionService(int threadCount, Lock lock) {
        for (int i = 0; i < threadCount; i++) {
            Condition condition = lock.newCondition();
            conditions.add(condition);
        }
    }

    public void manageCondition(int threadNumber) throws InterruptedException {
        while (threadNumber != requiredThreadNumber) {
            conditions.get(threadNumber).await();
        }
        requiredThreadNumber = getNextRequiredThreadNumber(requiredThreadNumber);
        conditions.get(requiredThreadNumber).signal();
    }

    private int getNextRequiredThreadNumber(int currentRequiredThreadNumber) {
        if (currentRequiredThreadNumber == (conditions.size() - 1)) {
            return 0;
        } else {
            return currentRequiredThreadNumber + 1;
        }
    }
}
