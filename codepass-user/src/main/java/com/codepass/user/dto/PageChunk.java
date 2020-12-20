package com.codepass.user.dto;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageChunk {
    private List<?> content = new ArrayList<>();
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int numberOfElements;

    public PageChunk(Page<?> page) {
        this.setContent(page.getContent());
        this.setTotalPages(page.getTotalPages());
        this.setTotalElements(page.getTotalElements());
        this.setPageNumber(page.getPageable().getPageNumber() + 1);
        this.setNumberOfElements(page.getNumberOfElements());
    }

    public List<?> getContent() {
        return content;
    }

    public void setContent(List<?> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}