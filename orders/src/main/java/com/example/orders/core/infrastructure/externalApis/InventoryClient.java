package com.example.orders.core.infrastructure.externalApis;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Inventory", path = "api/v1/inventory")  // Use the service name registered in Eureka
public interface InventoryClient {

    @GetMapping("/productExist")
    ResponseEntity<Integer> isProductExist(@RequestParam(name = "id") Integer id, @RequestParam(name = "ProductName") String name);

    @PostMapping("/reserveProduct")
    ResponseEntity<Boolean> reserveProduct(@RequestParam(name = "productId") int id,@RequestParam(name = "productQuantity") int quantity);
}
