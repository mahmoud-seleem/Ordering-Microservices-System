package com.example.inventory.presentation.ControllerInterface;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * API Definition for Inventory Operations.
 * Contains documentation for endpoints related to inventory checks and reservations.
 */
@Tag(name = "Inventory API", description = "Endpoints for checking product existence and reserving products in inventory.")
interface InventoryAPI {

    @Operation(summary = "Check Product Existence and Quantity",
            description = "Checks if a product exists by ID and name, returns its current quantity if found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found, quantity returned in body",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) // No content for 404
    })
    ResponseEntity<Integer> isProductExist(
            @Parameter(description = "ID of the product to check", required = true, example = "1")
            @RequestParam(name = "id") Integer id,
            @Parameter(description = "Name of the product to check", required = true, example = "Laptop")
            @RequestParam(name = "ProductName") String name);


    @Operation(summary = "Reserve Product Quantity",
            description = "Attempts to reserve a specified quantity of a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product quantity reserved successfully",
                    content = @Content), // No body needed for success boolean flag handled via status
            @ApiResponse(responseCode = "404", description = "Product not found or insufficient quantity",
                    content = @Content)
    })
    ResponseEntity<Boolean> reserveProduct(
            @Parameter(description = "ID of the product to reserve", required = true, example = "1")
            @RequestParam(name = "productId") int productId, // Changed name to productId for consistency
            @Parameter(description = "Quantity of the product to reserve", required = true, example = "5")
            @RequestParam(name = "productQuantity") int quantity);
}