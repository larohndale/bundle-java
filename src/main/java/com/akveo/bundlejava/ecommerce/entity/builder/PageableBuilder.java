package com.akveo.bundlejava.ecommerce.entity.builder;

import com.akveo.bundlejava.ecommerce.entity.enums.SortOrder;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableBuilder {
    public Pageable build(OrderGridFilter filter) {
        int pageNumber = filter.getPageNumber();
        int pageSize = filter.getPageSize();
        String sortField = filter.getSortBy();
        String orderBy = filter.getOrderBy() == null ? "empty" : filter.getOrderBy();
        Pageable pageable = null;
        switch(SortOrder.valueOfIgnoreCase(orderBy)){
            case ASC:
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
                break;
            case DESC:
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());
                break;
            case EMPTY:
                pageable = PageRequest.of(pageNumber, pageSize);
        }
        return pageable;
    }
}
