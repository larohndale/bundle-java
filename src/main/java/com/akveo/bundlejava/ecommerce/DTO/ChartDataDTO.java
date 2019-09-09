package com.akveo.bundlejava.ecommerce.DTO;

import java.util.List;

public class ChartDataDTO<T> {
    private List<T> values;
    private String type;

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
