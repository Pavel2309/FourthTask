package com.stakhiyevich.threadtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {

    private static final Logger logger = LogManager.getLogger();

    private static final int SLEEP_TIME = 1;
    private static final double MIN_LOAD_FACTOR = 0.25;
    private static final double MAX_LOAD_FACTOR = 0.75;
    private static final int CAPACITY = 100;

    private static final Lock lock = new ReentrantLock(true);
    private static final Condition notFull = lock.newCondition();
    private static final Condition notEmpty = lock.newCondition();
    private final AtomicInteger goodsQuantity = new AtomicInteger();

    public void addGoods(int quantity) {
        lock.lock();
        try {
            while (goodsQuantity.get() == CAPACITY || goodsQuantity.get() + quantity >= CAPACITY) {
                notFull.await();
                logger.info("waiting for free space in the storage");
            }
            goodsQuantity.addAndGet(quantity);
            TimeUnit.SECONDS.sleep(SLEEP_TIME);
            notEmpty.signal();
            logger.info("{} goods have been ADDED, new quantity: {}", quantity, getCurrentQuantity());
        } catch (InterruptedException e) {
            logger.error("can't add goods to the storage", e);
        } finally {
            lock.unlock();
        }
    }

    public void takeGoods(int quantity) {
        lock.lock();
        try {
            while (goodsQuantity.get() < quantity) {
                notEmpty.await();
                logger.info("waiting for more goods in the storage");
            }
            int currentQuantity = goodsQuantity.get();
            goodsQuantity.set(currentQuantity - quantity);
            TimeUnit.SECONDS.sleep(SLEEP_TIME);
            notFull.signal();
            logger.info("{} goods have been TAKEN, new quantity: {}", quantity, getCurrentQuantity());
        } catch (InterruptedException e) {
            logger.error("can't take goods from the storage", e);
        } finally {
            lock.unlock();
        }
    }

    public int getCurrentQuantity() {
        return goodsQuantity.get();
    }

    public void manageGoodsQuantity() {
        lock.lock();
        if (goodsQuantity.get() == 0 || goodsQuantity.get() < CAPACITY * MIN_LOAD_FACTOR) {
            goodsQuantity.set((int) (goodsQuantity.get() + CAPACITY * MIN_LOAD_FACTOR));
            notEmpty.signal();
            logger.info("goods quantity has been UPDATED by magic, new quantity: {}", getCurrentQuantity());
        } else if (goodsQuantity.get() > CAPACITY * MAX_LOAD_FACTOR) {
            goodsQuantity.set((int) (goodsQuantity.get() - CAPACITY * MIN_LOAD_FACTOR));
            notFull.signal();
            logger.info("goods quantity has been UPDATED by magic, new quantity: {}", getCurrentQuantity());
        }
        lock.unlock();
    }
}
