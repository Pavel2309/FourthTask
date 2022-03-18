package com.stakhiyevich.threadtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public class TimerThread extends TimerTask {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        logisticsCenter.adjustStorage();
        logger.info("storage has been updated!, current quantity: {}", logisticsCenter.getCurrentStorageQuantity());
    }
}



