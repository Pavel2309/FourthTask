package com.stakhiyevich.threadtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Terminal {

    private static final Logger logger = LogManager.getLogger();

    public void loadTruck(Truck truck) {
        logger.info("the truck id: {}, priority: {} quantity: {} , capacity: {} arrived for {} : {}", truck.getId(), truck.isPrioritized(), truck.getGoodsQuantity(), truck.getCapacity(), truck.getTask().getTaskType(), truck.getTask().getQuantity());
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        logisticsCenter.takeGoodsFromStorage(truck.getTask().getQuantity());
        truck.setGoodsQuantity(truck.getGoodsQuantity() + truck.getTask().getQuantity());
        logger.info("the truck id: {}, priority: {}, finished {}, new quantity: {}", truck.getId(), truck.isPrioritized(), truck.getTask().getTaskType(), truck.getGoodsQuantity());
    }

    public void unloadTruck(Truck truck) {
        logger.info("the truck id: {}, priority: {} quantity: {} , capacity: {} arrived for {} : {}", truck.getId(), truck.isPrioritized(), truck.getGoodsQuantity(), truck.getCapacity(), truck.getTask().getTaskType(), truck.getTask().getQuantity());
        LogisticsCenter logisticsCenter = LogisticsCenter.getInstance();
        logisticsCenter.addGoodsToStorage(truck.getTask().getQuantity());
        truck.setGoodsQuantity(truck.getGoodsQuantity() - truck.getTask().getQuantity());
        logger.info("the truck id: {}, priority: {}, finished {}, new quantity: {}", truck.getId(), truck.isPrioritized(), truck.getTask().getTaskType(), truck.getGoodsQuantity());
    }
}
