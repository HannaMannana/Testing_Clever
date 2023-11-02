package ru.clevertec.product.mapper.impl;

import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        return product;
    }

    @Override
    public InfoProductDto toInfoProductDto(Product product) {
        InfoProductDto dto = new InfoProductDto();
        dto.setUuid(product.getUuid());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;

    }

    @Override
    public Product merge(Product product, ProductDto productDto) {

        Product.ProductBuilder prod = Product.builder();
        if(product != null) {
            prod.uuid(product.getUuid());
            prod.created(product.getCreated());
        }
        if(productDto != null) {
            prod.name(productDto.getName());
            prod.description(productDto.getDescription());
            prod.price(productDto.getPrice());
        }

        return prod.build();
    }
}
