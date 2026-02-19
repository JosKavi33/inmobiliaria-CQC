package com.jose.inmobiliaria.property.service.api.dto.response;

import org.springframework.data.domain.Page;

public class PageMeta {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PageMeta(Page<?> pageData) {
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalElements = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
        this.last = pageData.isLast();
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}
