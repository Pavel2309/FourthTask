package com.stakhiyevich.threadtask.parser;

import com.stakhiyevich.threadtask.entity.Truck;
import com.stakhiyevich.threadtask.exception.ParserException;

import java.util.List;

public interface TruckDataParser {
    List<Truck> parseData(String value) throws ParserException;
}
