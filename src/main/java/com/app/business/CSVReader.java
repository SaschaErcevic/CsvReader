package com.app.business;

import java.io.InputStream;

public class CSVReader {

    public InputStream readFile(String fileLocation) {

        return getClass().getClassLoader().getResourceAsStream(fileLocation);
    }
}
