package ru.clevertec.product.data;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
public class ProductDto {
    public ProductDto(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * {@link ru.clevertec.product.entity.Product}
     */
    String name;


    /**
     * {@link ru.clevertec.product.entity.Product}
     */
    String description;


    /**
     * {@link ru.clevertec.product.entity.Product}
     */
    BigDecimal price;
}
