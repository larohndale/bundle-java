package com.akveo.bundlejava.ecommerce.service;

import com.akveo.bundlejava.authentication.exception.CountryNotFoundHttpException;
import com.akveo.bundlejava.ecommerce.DTO.CountryDTO;
import com.akveo.bundlejava.ecommerce.entity.Country;
import com.akveo.bundlejava.ecommerce.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CountryService {
    private ModelMapper modelMapper;
    private CountryRepository countryRepository;

    @Autowired
    CountryService(CountryRepository countryRepository,
                   ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }


    public List<CountryDTO> getList() {
        return countryRepository.findAll().stream()
                .map(country -> modelMapper.map(country, CountryDTO.class))
                .collect(toList());
    }

//    private List<CountryDTO> getList() {
//        List<CountryDTO> countryList = new ArrayList<>();
//        for(Country country: countryRepository.findAll())
//            countryList.add(modelMapper.map(country, CountryDTO.class));
//        return countryList;
//    }





    public CountryDTO getById(Long id) {
        Country existingCountry = countryRepository.findById(id).orElseThrow(
                () -> new CountryNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND)
        );

        return modelMapper.map(existingCountry, CountryDTO.class);
    }


}
