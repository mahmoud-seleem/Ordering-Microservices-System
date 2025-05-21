package com.example.orders.core.application.services;

import com.example.orders.core.application.dto.OrderDetailsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDetailsDto createNewOrder(OrderDetailsDto orderDetailsDto) throws Exception;
    OrderDetailsDto updateOrder(OrderDetailsDto orderDetailsDto) throws Exception;
    List<OrderDetailsDto> getAllOrders() throws Exception;
    OrderDetailsDto getOrderById(Long orderId) throws Exception;
    OrderDetailsDto deleteOrderById(Long orderId) throws Exception;
}
