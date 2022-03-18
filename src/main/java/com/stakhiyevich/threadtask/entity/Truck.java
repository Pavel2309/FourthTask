package com.stakhiyevich.threadtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck implements Runnable {

    private static final Logger logger = LogManager.getLogger();

    private long id;
    private int currentCapacity;
    private int maxCapacity;
    private boolean isPrioritized;
    private TruckTask task;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isPrioritized() {
        return isPrioritized;
    }

    public void setPrioritized(boolean prioritized) {
        isPrioritized = prioritized;
    }

    public TruckTask getTask() {
        return task;
    }

    public void setTask(TruckTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        Terminal terminal = logisticsCenter.getTerminal();
        switch (getTask()) {
            case LOAD -> {
                terminal.loadTruck(this);
                logger.info("truck loaded, current storage quantity: {}", logisticsCenter.getCurrentStorageQuantity());
            }
            case UNLOAD -> {
                terminal.unloadTruck(this);
                logger.info("truck unloaded, current storage quantity: {}", logisticsCenter.getCurrentStorageQuantity());
            }
        }
        logisticsCenter.releaseTerminal(terminal);
    }
}
