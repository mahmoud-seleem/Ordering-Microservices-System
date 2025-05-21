package com.example.orders.presentation.rest.controllers;


import com.example.orders.core.application.dto.OrderDetailsDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Order Management", description = "Operations related to order management")  // Grouping for Swagger UI
public interface OrderControllerInterface {

        @Operation(summary = "Create a new order", description = "Create a new order in the system")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully created a new order"),
                @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
        })
        @PostMapping("/new")
        OrderDetailsDto createNewOrder(@RequestBody OrderDetailsDto orderDetailsDto) throws Exception;

        @Operation(summary = "Update an existing order", description = "Update the details of an existing order")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully updated the order"),
                @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @PutMapping("/update")
        OrderDetailsDto updateOrder(@RequestBody OrderDetailsDto orderDetailsDto) throws Exception;

        @Operation(summary = "Get all orders", description = "Fetch all orders from the system")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved all orders")
        })
        @GetMapping("/all")
        List<OrderDetailsDto> getAllOrders() throws Exception;

        @Operation(summary = "Get order by ID", description = "Fetch an order by its unique ID")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the order"),
                @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @GetMapping("/{orderId}")
        OrderDetailsDto getOrderById(@PathVariable Long orderId) throws Exception;

        @Operation(summary = "Delete order by ID", description = "Delete an order by its unique ID")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully deleted the order"),
                @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @DeleteMapping("/{orderId}")
        OrderDetailsDto deleteOrderById(@PathVariable Long orderId) throws Exception;}
