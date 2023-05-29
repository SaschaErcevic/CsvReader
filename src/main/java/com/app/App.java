package com.app;

import com.app.business.CSVReader;
import com.app.business.DocumentCreator;
import com.app.entity.Document;
import com.app.ui.ConsolePrinter;
import com.app.util.ApplicationInputHandler;

import java.io.InputStream;

public class App {

    public static void main(String[] args) {
        args = ApplicationInputHandler.setArgsIfMissing(args);

        CSVReader csvReader = new CSVReader();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        DocumentCreator documentCreator = new DocumentCreator();

        InputStream inputStream = csvReader.readFile(args[0]);
        Document document =
                documentCreator.createDocumentFromInputstream(inputStream, Integer.parseInt(args[1]));
        consolePrinter.printDocument(document);
    }
}
