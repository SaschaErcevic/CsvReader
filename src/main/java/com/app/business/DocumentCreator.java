package com.app.business;

import com.app.entity.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DocumentCreator {

    RowCreator rowCreator = new RowCreator();

    public Document createDocumentFromInputstream(InputStream inputStream, Integer pageLength) {
        Document document = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            document = new Document();
            document.setPageLength(pageLength);

            document.setHeader(rowCreator.createRowFromLine(reader.readLine()));
            String line;
            while ((line = reader.readLine()) != null) {
                document.addRow(rowCreator.createRowFromLine(line));
            }
        } catch (IOException e) {
            System.out.println("Something went wrong reading the file");
        }
        document.calculateAndSetMaxNumberOfPages(pageLength);
        document.setStartOfPage(0);
        document.setEndOfPage(Math.min(pageLength, document.getRows().size()));
        document.addDataNumbers();
        document.calculateMaxWidthPerColumnOnCurrentPage();
        return document;
    }
}
