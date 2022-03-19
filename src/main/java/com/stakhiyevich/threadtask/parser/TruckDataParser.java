package com.stakhiyevich.threadtask.parser;

import com.stakhiyevich.threadtask.entity.Truck;

import java.util.List;

public interface TruckDataParser {
    List<Truck> parseData(String value);
}
