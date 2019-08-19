package com.akveo.bundlejava.ecommerce.entity.filter;

import org.springframework.web.bind.annotation.PathVariable;


public class OrderGridFilter extends BaseFilter {
    private String filterByname;
    private String filterBydate;
    private String filterBysum;
    private String filterBytype;
    private String filterBystatus;
    private String filterBycountry;

    public OrderGridFilter(int pageNumber, int pageSize, String sortBy, String orderBy, String filterByname, String filterBydate, String filterByvalue, String filterBytype, String filterBystatus, String filterBycountry) {
        super(pageNumber, pageSize, sortBy, orderBy);
        this.filterByname = filterByname;
        this.filterBydate = filterBydate;
        this.filterBysum = filterByvalue;
        this.filterBytype = filterBytype;
        this.filterBystatus = filterBystatus;
        this.filterBycountry = filterBycountry;
    }

    public String getFilterByName() {
        return filterByname;
    }

    public void setFilterByName(String filterByname) {
        this.filterByname = filterByname;
    }

    public String getFilterByDate() {
        return filterBydate;
    }

    public void setFilterByDate(String filterBydate) {
        this.filterBydate = filterBydate;
    }

    public String getFilterByValue() {
        return filterBysum;
    }

    public void setFilterByValue(String filterByvalue) {
        this.filterBysum = filterByvalue;
    }

    public String getFilterByType() {
        return filterBytype;
    }

    public void setFilterByType(String filterBytype) {
        this.filterBytype = filterBytype;
    }

    public String getFilterByStatus() {
        return filterBystatus;
    }

    public void setFilterByStatus(String filterBystatus) {
        this.filterBystatus = filterBystatus;
    }

    public String getFilterByCountry() {
        return filterBycountry;
    }

    public void setFilterByCountry(String filterBycountry) {
        this.filterBycountry = filterBycountry;
    }
}
