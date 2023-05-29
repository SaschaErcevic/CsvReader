package com.app.ui;

import com.app.entity.Document;
import com.app.util.StringFormatter;

public class ConsolePrinter {

    boolean running = true;
    ConsoleReader consoleReader = new ConsoleReader();
    StringFormatter stringFormatter = new StringFormatter();

    public void printDocument(Document document) {
        while (running) {
            printLine(
                    stringFormatter.buildLine(
                            document.getHeader(), "header", document.getMaxWidthPerColumnOnCurrentPage()));
            printLine(
                    stringFormatter.buildLine(
                            null, "seperator", document.getMaxWidthPerColumnOnCurrentPage()));
            for (int i = document.getStartOfPage(); i < document.getEndOfPage(); i++) {
                printLine(
                        stringFormatter.buildLine(
                                document.getRows().get(i), "row", document.getMaxWidthPerColumnOnCurrentPage()));
            }
            running = document.handleInput(printInputLineAndGetUserInput(), document);
        }
    }

    private void printLine(String line) {
        System.out.println(line);
    }

    private String printInputLineAndGetUserInput() {
        System.out.println("F)irst Page, P)revious Page, N)ext Page, L)ast Page, E)xit");
        return consoleReader.readUserInput();
    }
}
