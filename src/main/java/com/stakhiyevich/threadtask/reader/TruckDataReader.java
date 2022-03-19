package com.stakhiyevich.threadtask.reader;

import com.stakhiyevich.threadtask.exception.ReaderException;

public interface TruckDataReader {
    String readData(String filePath) throws ReaderException;
}
