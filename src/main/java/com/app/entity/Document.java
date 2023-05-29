package com.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Document extends DocumentHandler {
    private Integer currentPage = 0;
    private Integer pageLength;
    private List<String> header;
    private Integer startOfPage;
    private Integer endOfPage;
    private Integer numberOfPages;
    private List<List<String>> rows = new ArrayList<>();
    private List<Integer> maxWidthPerColumnOnCurrentPage = new ArrayList<>();

    public Document() {
        super.setDocument(this);
    }
}
