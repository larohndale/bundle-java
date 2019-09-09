package com.akveo.bundlejava.ecommerce.entity.builder;


import com.akveo.bundlejava.ecommerce.entity.Country;
import com.akveo.bundlejava.ecommerce.entity.Order;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import com.akveo.bundlejava.ecommerce.entity.specification.OrderSpecification;
import com.akveo.bundlejava.ecommerce.entity.specification.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecificationBuilder {

    private List<SearchCriteria> params;

    public SpecificationBuilder() {
        params = new ArrayList<>();
    }

    private SpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    private void initParams(OrderGridFilter filter){
        if (filter.getFilterByName() != null) {
            with("name", ":", filter.getFilterByName());
        }
        if (filter.getFilterByDate() != null) {
            with("date", ":", filter.getFilterByDate());
        }
        if (filter.getFilterByValue() != null) {
            with("value", ":", filter.getFilterByValue());
        }
        if (filter.getFilterByCountry() != null) {

            with("country", ":", filter.getFilterByCountry());
        }
        if (filter.getFilterByStatus() != null) {
            with("status", ":", filter.getFilterByStatus());
        }
        if (filter.getFilterByType() != null) {
            with("type", ":", filter.getFilterByType());
        }
    }

    public Specification<Order> build(OrderGridFilter filter) {
        params.clear();
        initParams(filter);

        if (params.size() == 0) {
            return null;
        }

        Specification<Order> result = new OrderSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new OrderSpecification(params.get(i)));
        }

        return result;
    }
}

