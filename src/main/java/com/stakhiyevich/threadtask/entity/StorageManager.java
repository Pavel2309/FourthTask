package com.stakhiyevich.threadtask.entity;

import java.util.TimerTask;

public class StorageManager extends TimerTask {

    @Override
    public void run() {
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        logisticsCenter.adjustStorage();
    }
}



