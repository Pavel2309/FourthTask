package com.stakhiyevich.threadtask.parser.impl;

import com.stakhiyevich.threadtask.entity.Truck;
import com.stakhiyevich.threadtask.entity.TruckTask;
import com.stakhiyevich.threadtask.parser.TruckDataParser;

import java.util.ArrayList;
import java.util.List;

public class TruckDataParserImpl implements TruckDataParser {

    private static final String COMA_SPLITTER = ",";
    private static final String DOUBLE_COLON_SPLITTER = "::";

    @Override
    public List<Truck> parseData(String value) {
        String[] trucksString = value.split(COMA_SPLITTER);
        List<Truck> trucks = new ArrayList<>();

        for (String singleTruckString : trucksString) {
            String[] truckString = singleTruckString.split(DOUBLE_COLON_SPLITTER);
            Truck truck = createTruckObject(
                    Long.parseLong(truckString[0]),
                    Integer.parseInt(truckString[1]),
                    Integer.parseInt(truckString[2]),
                    Boolean.parseBoolean(truckString[3]),
                    TruckTask.TaskType.valueOf(truckString[4]),
                    Integer.parseInt(truckString[5]));
            trucks.add(truck);
        }
        return trucks;
    }

    private Truck createTruckObject(long id, int currentQuantity, int capacity, boolean isPrioritized, TruckTask.TaskType taskType, int taskQuantity) {
        Truck truck = new Truck();
        TruckTask truckTask = new TruckTask();
        truck.setId(id);
        truck.setGoodsQuantity(currentQuantity);
        truck.setCapacity(capacity);
        truck.setPrioritized(isPrioritized);
        truckTask.setTaskType(taskType);
        truckTask.setQuantity(taskQuantity);
        truck.setTask(truckTask);
        return truck;
    }
}






