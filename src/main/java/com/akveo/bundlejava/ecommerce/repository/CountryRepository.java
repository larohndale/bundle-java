package com.akveo.bundlejava.ecommerce.repository;

import com.akveo.bundlejava.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
