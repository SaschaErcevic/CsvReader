package com.app.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DocumentHandler {

    private Document document;

    public void setDocument(Document document) {
        this.document = document;
    }

    public void addRow(List<String> row) {
        document.getRows().add(row);
    }

    public void calculateAndSetStartOfPage() {
        document.setStartOfPage(document.getCurrentPage() * document.getPageLength());
    }

    public void calculateAndSetEndOfPage() {
        document.setEndOfPage(Math.min(document.getRows().size(), document.getStartOfPage() + document.getPageLength()));
    }

    public void calculateMaxWidthPerColumnOnCurrentPage() {
        int startIndex = document.getStartOfPage();
        int endIndex = document.getEndOfPage();

        int numColumns = document.getRows().get(0).size();
        Integer[] columnLengths = Stream.generate(() -> 0)
                .limit(numColumns)
                .toArray(Integer[]::new);

        for (int i = startIndex; i < endIndex; i++) {
            List<String> row = document.getRows().get(i);
            findLongestColumnInRow(columnLengths, row, numColumns);
        }
        findLongestColumnInRow(columnLengths, document.getHeader(), document.getHeader().size());
        document.setMaxWidthPerColumnOnCurrentPage(List.of(columnLengths));
    }

    public void findLongestColumnInRow(Integer[] columnLengths, List<String> row, int numColumns) {
        for (int i = 0; i < numColumns; i++) {
            String value = row.get(i);
            int length = value.length();
            if (length > columnLengths[i]) {
                columnLengths[i] = length;
            }
        }
    }

    public boolean handleInput(String input, Document document) {
        switch (input) {
            case "F" -> document.setCurrentPage(0);
            case "P" -> document.setCurrentPage(Math.max(document.getCurrentPage() - 1, 0));
            case "N" -> document.setCurrentPage(Math.min(document.getCurrentPage() + 1, document.getRows().size() / document.getPageLength()));
            case "L" -> document.setCurrentPage(document.getRows().size() / document.getPageLength());
            case "E" -> {
                return false;
            }
            default -> System.out.println("Unknown command.");
        }
        calculateAndSetStartOfPage();
        calculateAndSetEndOfPage();
        calculateMaxWidthPerColumnOnCurrentPage();
        return true;
    }

    public void calculateAndSetMaxNumberOfPages(Integer pageLength) {
        document.setNumberOfPages(document.getRows().size() - 1 / pageLength + 1);
    }

    public void addDataNumbers() {
        List<String> updatedHeader = new ArrayList<>(document.getHeader());
        updatedHeader.add(0, "No.");
        document.setHeader(updatedHeader);
        List<List<String>> updatedRows = new ArrayList<>();
        for (int i = 0; i < document.getRows().size(); i++) {
            List<String> newRow = new ArrayList<>(document.getRows().get(i));
            newRow.add(0, (i + 1) + ".");
            updatedRows.add(newRow);
        }
        document.setRows(updatedRows);
    }
}
