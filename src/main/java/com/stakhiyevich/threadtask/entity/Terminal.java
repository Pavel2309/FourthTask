package com.stakhiyevich.threadtask.entity;


public class Terminal {

    private long id;

    public void loadTruck(Truck truck) {
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        logisticsCenter.takeGoodsFromStorage(truck.getMaxCapacity());
    }

    public void unloadTruck(Truck truck) {
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        logisticsCenter.addGoodsToStorage(truck.getCurrentCapacity());
        truck.setCurrentCapacity(0);
    }
}
