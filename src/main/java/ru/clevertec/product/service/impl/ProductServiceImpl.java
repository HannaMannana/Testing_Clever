package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public InfoProductDto get(UUID uuid) throws RuntimeException {
        if(uuid == null) {
            throw new IllegalArgumentException("slides");
        }
        return mapper.toInfoProductDto(productRepository.findById(uuid).orElseThrow());
    }

    @Override
    public List<InfoProductDto> getAll() {
        List<Product> products = productRepository.findAll();

        List<InfoProductDto> dtosList = new ArrayList<>();
        for (Product product : products) {
            InfoProductDto dto = mapper.toInfoProductDto(product);
            dtosList.add(dto);
        }
        return dtosList;
    }

    @Override
    public UUID create(ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        productRepository.save(product);

        return product.getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {

        Product product = mapper.toProduct(productDto);
        product.setUuid(uuid);
        productRepository.save(product);

    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }
}

