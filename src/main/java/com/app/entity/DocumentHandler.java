package com.app.entity;

import com.app.ui.ConsoleReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentHandler {

    private Document document;
    private ConsoleReader consoleReader = new ConsoleReader();

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
        int pageToJumpTo = 0;
        if (input.charAt(0) == 'J') {
            try {
                pageToJumpTo = Integer.parseInt(input.substring(1)) - 1;
                input = "J";
            } catch (NumberFormatException e) {
                System.out.println("If you want to jump to a specific page use J directly followed by a number.\ne.g. J2 to jump to page 2.\n");
                return true;
            }
        }
        switch (input) {
            case "F" -> document.setCurrentPage(0);
            case "P" -> document.setCurrentPage(Math.max(document.getCurrentPage() - 1, 0));
            case "N" -> document.setCurrentPage(Math.min(document.getCurrentPage() + 1, document.getRows().size() / document.getPageLength()));
            case "L" -> document.setCurrentPage(document.getRows().size() / document.getPageLength());
            case "J" -> document.setCurrentPage(Math.max(0, Math.min(pageToJumpTo, document.getNumberOfPages() - 1)));
            case "S" -> {
                System.out.println("Please enter column name to sort on:");
                String column = consoleReader.readUserInput();
                sortRowsByColumn(column);
            }
            case "E" -> {
                return false;
            }
            default -> System.out.println("Unkown command.");
        }
        calculateAndSetStartOfPage();
        calculateAndSetEndOfPage();
        calculateMaxWidthPerColumnOnCurrentPage();
        return true;
    }

    public void calculateAndSetMaxNumberOfPages(Integer pageLength) {
        document.setNumberOfPages((document.getRows().size() - 1) / pageLength + 1);
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

    public void sortRowsByColumn(String column) {
        int columnNumber = document.getHeader().indexOf(column);
        if (columnNumber == -1) {
            return;
        }
        document.setRows(document.getRows().stream()
                .sorted(Comparator.comparing(row -> row.get(columnNumber)))
                .collect(Collectors.toList()));
        recalculateFirstColumn();
        calculateMaxWidthPerColumnOnCurrentPage();
    }

    private void recalculateFirstColumn() {
        int i = 1;
        for (List<String> row : document.getRows()) {
            row.set(0, i++ + ".");
        }
    }
}
