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
            printInfoLines(document.getCurrentPage(), document.getNumberOfPages());
            String input = consoleReader.readUserInput();
            running = document.handleInput(input, document);
        }
    }

    private void printLine(String line) {
        System.out.println(line);
    }

    private void printInfoLines(int currentPage, int maxPages) {
        System.out.println("Page " + (currentPage + 1) + " of " + maxPages);
        System.out.println("F)irst Page, P)revious Page, N)ext Page, L)ast Page, J)ump to Page, S)ort, E)xit");
    }
}
