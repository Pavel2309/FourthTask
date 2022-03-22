package com.stakhiyevich.threadtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticsCenter {

    private static final Logger logger = LogManager.getLogger();

    private static final int NUMBER_OF_TERMINALS = 5;
    private static final AtomicBoolean isLogisticsCenterCreated = new AtomicBoolean(false);

    private static final int TIMER_DELAY = 0;
    private static final int TIMER_INTERVAL = 5000;

    private static final Lock instanceLock = new ReentrantLock(true);
    private static final Lock terminalLock = new ReentrantLock(true);
    private static final Condition newAvailableTerminal = terminalLock.newCondition();

    private static LogisticsCenter instance = new LogisticsCenter();

    Timer timer;
    private final Storage storage;
    private final Deque<Terminal> availableTerminals;
    private final Deque<Terminal> usedTerminals;

    private LogisticsCenter() {
        storage = new Storage();
        timer = new Timer(true);
        timer.schedule(new StorageManager(), TIMER_DELAY, TIMER_INTERVAL);
        availableTerminals = new ArrayDeque<>(NUMBER_OF_TERMINALS);
        usedTerminals = new ArrayDeque<>(NUMBER_OF_TERMINALS);
        for (int i = 0; i < NUMBER_OF_TERMINALS; i++) {
            Terminal terminal = new Terminal();
            availableTerminals.add(terminal);
        }
    }

    public static LogisticsCenter getInstance() {
        if (!isLogisticsCenterCreated.get()) {
            instanceLock.lock();
            if (instance == null) {
                instance = new LogisticsCenter();
                isLogisticsCenterCreated.set(true);
            }
            instanceLock.unlock();
        }
        return instance;
    }

    public Terminal getTerminal() {
        terminalLock.lock();
        Terminal currentTerminal = null;
        try {
            while (availableTerminals.isEmpty()) {
                newAvailableTerminal.await();
                logger.info("waiting for an available terminal");
            }
            currentTerminal = availableTerminals.remove();
            usedTerminals.add(currentTerminal);
            logger.info("the terminal has been acquired, total used terminals: {}", usedTerminals.size());
        } catch (InterruptedException e) {
            logger.error("can't acquire a terminal", e);
        } finally {
            terminalLock.unlock();
        }
        return currentTerminal;
    }

    public void releaseTerminal(Terminal terminal) {
        terminalLock.lock();
        try {
            usedTerminals.remove(terminal);
            availableTerminals.add(terminal);
            newAvailableTerminal.signalAll();
            logger.info("the terminal has been released, total available terminals: {}", availableTerminals.size());
        } finally {
            terminalLock.unlock();
        }
    }

    public void addGoodsToStorage(int quantity) {
        storage.addGoods(quantity);
    }

    public void takeGoodsFromStorage(int quantity) {
        storage.takeGoods(quantity);
    }

    public int getCurrentStorageQuantity() {
        return storage.getCurrentQuantity();
    }

    public void adjustStorage() {
        storage.manageGoodsQuantity();
    }
}
