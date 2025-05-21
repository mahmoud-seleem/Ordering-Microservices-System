package com.example.inventory.presentation;

import com.example.inventory.entity.Product;
import com.example.inventory.service.InventoryService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/productExist")
    public ResponseEntity<Integer> isProductExist(@RequestParam(name = "id") Integer id, @RequestParam(name = "ProductName") String name){
        Integer result = inventoryService.isProductExist(id, name);
        if (result == -1)
            return ResponseEntity.notFound().build(); // 404
        return ResponseEntity.ok(result); // 200

    }



    @PostMapping("/reserveProduct")
    public ResponseEntity<Boolean> reserveProduct(@RequestParam(name = "productId") int id,@RequestParam(name = "productQuantity") int quantity){
        boolean result = inventoryService.reserveProduct(id, quantity);
        if(result)
           return ResponseEntity.ok().build(); // 200
        return ResponseEntity.notFound().build(); // 404
    }



}
