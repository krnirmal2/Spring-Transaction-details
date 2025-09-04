package com.distributedTransaction.handler;

import com.distributedTransaction.entity.Product;
import com.distributedTransaction.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryHandler {


    private final InventoryRepository inventoryRepository;

    public InventoryHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Product updateProductDetails(Product product) {

        //forcefully throwing exception to simulate use of tx
        if(product.getPrice() > 80000){
            throw new RuntimeException("DB crashed.....");
        }
        return inventoryRepository.save(product);
    }


    public Product getProduct(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Product not available with id : " + id)
                );
    }
}
