package com.akveo.bundlejava.ecommerce.DTO;

import com.akveo.bundlejava.ecommerce.DTO.CountryDTO;
import com.akveo.bundlejava.ecommerce.DTO.MoneyDTO;

import java.util.Date;

public class OrderDTO {
    public Long id;
    public String name;
    public Date date;
    public MoneyDTO sum;
    public String type;
    public String status;
    public CountryDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MoneyDTO getSum() {
        return sum;
    }

    public void setSum(MoneyDTO sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }
}