package com.akveo.bundlejava.ecommerce.service;

import com.akveo.bundlejava.authentication.exception.OrderNotFoundHttpException;
import com.akveo.bundlejava.ecommerce.DTO.OrderDTO;
import com.akveo.bundlejava.ecommerce.GridData;
import com.akveo.bundlejava.ecommerce.entity.Order;
import com.akveo.bundlejava.ecommerce.entity.builder.PageableBuilder;
import com.akveo.bundlejava.ecommerce.entity.enums.SortOrder;
import com.akveo.bundlejava.ecommerce.entity.filter.OrderGridFilter;
import com.akveo.bundlejava.ecommerce.entity.builder.SpecificationBuilder;
import com.akveo.bundlejava.ecommerce.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private SpecificationBuilder specificationBuilder;
    private ModelMapper modelMapper;
    private PageableBuilder pageableBuilder;

    @Autowired
    OrderService(OrderRepository orderRepository,
                 SpecificationBuilder specificationBuilder,
                 PageableBuilder pageableBuilder,
                 ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.specificationBuilder = specificationBuilder;
        this.pageableBuilder = pageableBuilder;
    }

    @Transactional
    public boolean delete(Long id) {
        try {
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

    private List<Order> getFilteredListWithTotalCount (Specification<Order> spec, Pageable pageable){
        Page<Order> pageData = orderRepository.findAll(spec,pageable);
        List<Order> filteredData = pageData.getContent();
        return filteredData;
    }

    private List<OrderDTO> parseOrdersToOrderDTO(List<Order> orders) {
        return orders.stream().map(order ->
            modelMapper.map(order, OrderDTO.class)
        ).collect(Collectors.toList());
    }

    public GridData<OrderDTO> getDataForGrid(OrderGridFilter filter){

//        List<OrderDTO> tuple = orderRepository.findAll().stream()
//                    .map(order -> modelMapper.map(order, OrderDTO.class))
//                    .collect(toList());

        Specification<Order> specification = specificationBuilder.build(filter);
        Pageable paginationAndSort = pageableBuilder.build(filter);

        List<Order> orders = getFilteredListWithTotalCount(specification, paginationAndSort);

        GridData<OrderDTO> gridData = new GridData<>();
        gridData.setItems(parseOrdersToOrderDTO(orders));
        gridData.setTotalCount(1000);//???
        return gridData;
    }



    @Transactional
    public OrderDTO updateOrderById(Long id, OrderDTO orderDTO){
        return update(id, orderDTO);
    }

    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundHttpException("User with id: " + id + " not found", HttpStatus.NOT_FOUND)
        );

        Order order = modelMapper.map(orderDTO, Order.class);

//        if (order.getCountry() == 0) {
//            order.setCountry(new Long(1));
//        }
//        order.setId(id);

        orderRepository.save(order);

        return modelMapper.map(order, OrderDTO.class);
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);

        orderRepository.save(order);

        return modelMapper.map(order, OrderDTO.class);
    }



}
