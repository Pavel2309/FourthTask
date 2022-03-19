package com.stakhiyevich.threadtask.reader.impl;

import com.stakhiyevich.threadtask.exception.ReaderException;
import com.stakhiyevich.threadtask.reader.TruckDataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruckDataReaderImpl implements TruckDataReader {

    private static final Logger logger = LogManager.getLogger();
    private static final String SPLITTER = ",";

    @Override
    public String readData(String filePath) throws ReaderException {
        String resultString;
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filePath);
        if (resource == null) {
            logger.error("file {} does not exist", filePath);
            throw new ReaderException("file " + filePath + " does not exist");
        }
        try (Stream<String> stream = Files.lines(Paths.get(resource.getPath()))) {
            resultString = stream.collect(Collectors.joining(SPLITTER));

        } catch (IOException e) {
            logger.error("can't read {}", filePath, e);
            throw new ReaderException("can't read " + filePath, e);
        }

        if (resultString.isEmpty()) {
            logger.error("the file is empty");
            throw new ReaderException("the file is empty");
        }

        return resultString;
    }
}
