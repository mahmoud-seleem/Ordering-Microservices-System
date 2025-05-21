package com.example.orders.shared.utils;

import com.example.inventory.ProductExistenceResponse;
import com.example.orders.core.application.dto.OrderDetailsDto;
import com.example.orders.core.application.dto.ProductDetailsDto;
import com.example.orders.core.domain.entities.Order;
import com.example.orders.core.infrastructure.externalApis.GRPCInventoryClient;
import com.example.orders.core.infrastructure.externalApis.InventoryClient;
import com.example.orders.core.infrastructure.repository.OrderRepo;
import com.example.orders.core.infrastructure.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Validation {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private GRPCInventoryClient grpcInventoryClient;

    public void validateNewOrderDetailsDto(OrderDetailsDto dto) throws IllegalAccessException {
        for(ProductDetailsDto productDetailsDto : dto.getProductsDetails()){
            validateProductDto(productDetailsDto);
        }
    }
    public void validateUpdateOrderDetailsDto(OrderDetailsDto dto) throws IllegalAccessException {
        for(ProductDetailsDto productDetailsDto : dto.getProductsDetails()){
            validateProductDto(productDetailsDto);
        }
    }

    public void isOrderExist(OrderDetailsDto dto){
        Order order = orderRepo.findById(dto.getOrderId()).get();
    }

    public void validateProductDto(ProductDetailsDto dto) throws IllegalAccessException {
        for(Field field : getAllFields(dto.getClass())){
            field.setAccessible(true);
            isNotNull(field.get(dto),field.getName());
        }
        isProductExistAndSufficient(dto);
        //isProductExistAndSufficientGrpcVersion(dto);
        }

    public void isProductExistAndSufficient(ProductDetailsDto dto){
        ResponseEntity<Integer> response;
        try{
            response = inventoryClient.isProductExist(dto.getProductId(),dto.getProductName());
        }catch (Exception e){
            System.out.println(e);
            throw new CustomValidationException(
                    "product with Id / Name is not exist!",
                    "productId / productName",
                    dto.getProductId()+" / "+dto.getProductName());
        }
        isStockSufficient(dto.getQuantity(),response.getBody());
    }
    public void isProductExistAndSufficientGrpcVersion(ProductDetailsDto dto){
        ProductExistenceResponse response;
        try{
            response = grpcInventoryClient.checkProductExistence(dto.getProductId(),dto.getProductName());
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomValidationException(
                    "product with Id / Name is not exist!",
                    "productId / productName",
                    dto.getProductId()+" / "+dto.getProductName());
        }
        isStockSufficient(dto.getQuantity(),response.getQuantity());
    }

    public void isStockSufficient(Integer quantity,Integer stock){
        if (stock  >= quantity){
            return;
        }
        throw new CustomValidationException(
                "product stock of this Id is not sufficient ",
                "available stock quantity",
                stock);
    }
    public void isNotNull(Object value, String name) {
        if (value == null) {
            throw new CustomValidationException("field can't be null !!", name, null);
        }
    }

    public void ensureFieldIsNull(Class clazz, Object object, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(clazz, name);
        field.setAccessible(true);
        if (field.get(object) != null) {
            field.set(object, null);
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Search in parent class
            }
        }
        return null; // Field not found
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields()).toList());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
