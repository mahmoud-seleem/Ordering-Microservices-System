package com.example.inventory.service;

import com.example.inventory.dto.ProductRequestDto;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public Integer isProductExist(Integer id, String name) {
        if(productRepository.existsByIdAndName(id, name))
            return productRepository.findById(id).get().getQuantity();
        else
            return -1;
    }

    public Boolean reserveProduct(int id, int quantity) {
        boolean flag = false;
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        if (product.getQuantity() - quantity >= 0){
            product.setQuantity(product.getQuantity() - quantity);
            flag = true;
        }

        else {
            try {
                throw new BadRequestException("Quantity ordered is more than stored");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        productRepository.save(product);
        return flag;
    }



    // update stock if the order is updated (addition or subtraction)


}
