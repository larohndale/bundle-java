package com.akveo.bundlejava.ecommerce.service;

import com.akveo.bundlejava.authentication.BundleUserDetailsService;
import com.akveo.bundlejava.authentication.exception.OrderNotFoundHttpException;
import com.akveo.bundlejava.ecommerce.DTO.OrderDTO;
import com.akveo.bundlejava.ecommerce.GridData;
import com.akveo.bundlejava.ecommerce.entity.Country;
import com.akveo.bundlejava.ecommerce.entity.Order;
import com.akveo.bundlejava.ecommerce.entity.builder.PageableBuilder;
import com.akveo.bundlejava.ecommerce.entity.enums.SortOrder;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import com.akveo.bundlejava.ecommerce.entity.builder.SpecificationBuilder;
import com.akveo.bundlejava.ecommerce.repository.CountryRepository;
import com.akveo.bundlejava.ecommerce.repository.OrderRepository;
import com.akveo.bundlejava.user.User;
import com.akveo.bundlejava.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CountryRepository countryRepository;
    private SpecificationBuilder specificationBuilder;
    private ModelMapper modelMapper;
    private PageableBuilder pageableBuilder;
    private UserRepository userRepository;

    @Autowired
    OrderService(OrderRepository orderRepository,
                 UserRepository userRepository,
                 CountryRepository countryRepository,
                 SpecificationBuilder specificationBuilder,
                 PageableBuilder pageableBuilder,
                 ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.specificationBuilder = specificationBuilder;
        this.pageableBuilder = pageableBuilder;
    }

    @Transactional
    public boolean delete(Long id) {
        try {
            //orderRepository.delete(id);
            orderRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new OrderNotFoundHttpException("Order with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public OrderDTO getOrderById(Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND)
        );

        return modelMapper.map(existingOrder, OrderDTO.class);
    }

    private Page<Order> getFilteredPageWithTotalCount (Specification<Order> spec, Pageable pageable){
        return orderRepository.findAll(spec,pageable);
    }

    private List<OrderDTO> parseOrdersToOrderDTO(List<Order> orders) {
        return orders.stream().map(order ->
                modelMapper.map(order, OrderDTO.class)
        ).collect(Collectors.toList());
    }

    private GridData<OrderDTO> parsePageToGridData(Page<Order> orderPages){
        GridData<OrderDTO> gridData = new GridData<>();
        List<Order> orderList = orderPages.getContent();
        long totalCount = orderPages.getTotalElements();
        gridData.setItems(parseOrdersToOrderDTO(orderList));
        gridData.setTotalCount(totalCount);
        return gridData;
    }

    public GridData<OrderDTO> getDataForGrid(OrderGridFilter filter){
        Specification<Order> specification = specificationBuilder.build(filter);
        Pageable paginationAndSort = pageableBuilder.build(filter);

        Page<Order> orderPages = getFilteredPageWithTotalCount(specification, paginationAndSort);

        return parsePageToGridData(orderPages);
    }

    @Transactional
    public OrderDTO updateOrderById(Long id, OrderDTO orderDTO, Authentication auth){
        return update(id, orderDTO, auth);
    }

    public OrderDTO update(Long id, OrderDTO orderDTO, Authentication auth) {
        Order orderFromDB = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND)
        );

        User createdUser = orderFromDB.getCreatedByUserId();

        Long userId = ((BundleUserDetailsService.BundleUserDetails)auth.getPrincipal()).getUser().getId();
        User updatedUser = userRepository.getOne(userId);

        Order order = modelMapper.map(orderDTO, Order.class);//tune mapping to orderFromDB

        Long countryId = orderFromDB.getCountry().getId();
        if (countryId == 0) {
            Country country = countryRepository.getOne(countryId);
            orderFromDB.setCountry(country);
        }

        order.setUpdatedByUserId(updatedUser);
        order.setCreatedByUserId(createdUser);
        orderRepository.save(order);

        return orderDTO;
    }

    @Transactional
    public OrderDTO createOrder(Authentication auth, OrderDTO orderDTO) {
        Long userId = ((BundleUserDetailsService.BundleUserDetails)auth.getPrincipal()).getUser().getId();
        User user = userRepository.getOne(userId);

        Order order = modelMapper.map(orderDTO, Order.class);

        order.setCreatedByUserId(user);
        order.setUpdatedByUserId(user);
        orderRepository.save(order);

        return orderDTO;
    }
}
