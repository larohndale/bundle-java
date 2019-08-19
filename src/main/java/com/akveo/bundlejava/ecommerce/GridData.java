package com.akveo.bundlejava.ecommerce;

import java.util.List;

public class GridData<DTO> {
    private int totalCount;
    private List<DTO> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<DTO> getItems() {
        return items;
    }

    public void setItems(List<DTO> items) {
        this.items = items;
    }
}
