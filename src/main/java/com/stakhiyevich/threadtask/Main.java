package com.stakhiyevich.threadtask;

import com.stakhiyevich.threadtask.entity.StorageManager;
import com.stakhiyevich.threadtask.entity.Truck;
import com.stakhiyevich.threadtask.entity.TruckQueue;
import com.stakhiyevich.threadtask.exception.ReaderException;
import com.stakhiyevich.threadtask.parser.TruckDataParser;
import com.stakhiyevich.threadtask.parser.impl.TruckDataParserImpl;
import com.stakhiyevich.threadtask.reader.TruckDataReader;
import com.stakhiyevich.threadtask.reader.impl.TruckDataReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static final String TRUCK_FILE = "data/TruckData.txt";
    private static final int POOL_SIZE = 5;
    private static final int TIMER_DELAY = 0;
    private static final int TIMER_INTERVAL = 5000;

    public static void main(String[] args) {

        TruckDataReader dataReader = new TruckDataReaderImpl();
        TruckDataParser dataParser = new TruckDataParserImpl();
        TruckQueue truckQueue = new TruckQueue();
        List<Truck> inputTrucks = new ArrayList<>();

        String stringOfTrucks;
        try {
            stringOfTrucks = dataReader.readData(TRUCK_FILE);
            inputTrucks = dataParser.parseData(stringOfTrucks);
        } catch (ReaderException e) {
            logger.error("can't read or parse file", e);
        }

        truckQueue.addTrucksFromList(inputTrucks);
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

        truckQueue.getTruckQueue().forEach(executorService::execute);
        executorService.shutdown();

    }
}
