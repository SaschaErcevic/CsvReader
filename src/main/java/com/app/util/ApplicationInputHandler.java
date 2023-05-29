package com.app.util;

public class ApplicationInputHandler {

    private static final String FILENAME = "csv.csv";
    private static final String PAGE_LENGTH = "3";

    public static String[] setArgsIfMissing(String[] args) {
        if (args.length == 0) {
            return new String[]{FILENAME, PAGE_LENGTH};
        } else if (args.length == 1) {
            return new String[]{args[0], PAGE_LENGTH};
        }
        return args;
    }
}
