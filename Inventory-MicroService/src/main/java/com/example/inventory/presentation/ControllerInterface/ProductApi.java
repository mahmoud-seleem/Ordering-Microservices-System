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


@Tag(name = "Product API", description = "Endpoints for Creating, Reading, Updating, and Deleting Products.")
public interface ProductApi {

    @Operation(summary = "Create a new product", description = "Adds a new product to the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    ResponseEntity<ProductResponseDto> createProduct(
            @Parameter(description = "Product data for creation", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
            @RequestBody ProductRequestDto productRequestDto);


    @Operation(summary = "Get all products", description = "Retrieves a list of all products.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))) // Can refine schema further if needed
    ResponseEntity<List<ProductResponseDto>> getAllProducts();


    @Operation(summary = "Get product by ID", description = "Retrieves details for a specific product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    ResponseEntity<ProductResponseDto> getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true, example = "1")
            @PathVariable Integer id);


    @Operation(summary = "Update an existing product", description = "Updates details for an existing product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    ResponseEntity<ProductResponseDto> updateProduct(
            @Parameter(description = "ID of the product to update", required = true, example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Updated product data", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
            @RequestBody ProductRequestDto productRequestDto);


    @Operation(summary = "Delete a product", description = "Deletes a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true, example = "1")
            @PathVariable Integer id);
}