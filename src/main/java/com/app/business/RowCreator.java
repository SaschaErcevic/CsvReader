package com.app.business;

import java.util.List;

public class RowCreator {

    public List<String> createRowFromLine(String line) {
        String[] entries = line.split(";");
        return List.of(entries);
    }
}
