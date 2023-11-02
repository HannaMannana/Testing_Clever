package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> productList = new ArrayList<>();

    InMemoryProductRepository() {
        productList.add(new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN));
        productList.add(new Product(UUID.fromString("9b77a736-e604-4495-8e55-73e4d56460e4"),
                "Нига", "Не интересная", BigDecimal.valueOf(18.00), LocalDateTime.MIN));
    }


    @Override
    public Optional<Product> findById(UUID uuid) {
        for (Product product : productList) {
            if (product.getUuid().equals(uuid)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is null");
        }
        if (findById(product.getUuid()).equals(Optional.empty())) {
            productList.add(product);
        } else {
            for (Product updatedProduct : productList) {
                if (product.getUuid().equals(updatedProduct.getUuid())) {
                    productList.remove(updatedProduct);
                    productList.add(product);
                }
            }
        }
        return product;
    }

    @Override
    public void delete(UUID uuid) {
        productList.removeIf(product -> uuid.equals(product.getUuid()));
    }

}

