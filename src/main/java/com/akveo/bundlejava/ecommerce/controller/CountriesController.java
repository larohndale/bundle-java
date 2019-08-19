package com.akveo.bundlejava.ecommerce.controller;


import com.akveo.bundlejava.ecommerce.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for managing countries
 */

@Controller
@RequestMapping("/countries")
public class CountriesController {

    private CountryService countryService;

    @Autowired
    public CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("")
    public ResponseEntity getAll() {
        return ok(countryService.getList());
    }


}
