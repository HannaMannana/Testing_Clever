package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductMapperImplTest {

    private ProductMapper productMapper = new ProductMapperImpl();

    @Test
    void toProductShouldReturnNewProductFromProductDto() {

        // given
        ProductDto dto = new ProductDto("Книга", "Интересная", BigDecimal.valueOf(18.25));
        Product expected = new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"), "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN);

        // when
        Product actual = productMapper.toProduct(dto);


        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
    }

    @Test
    void toInfoProductDtoShouldReturnNewInfoProductDtoFromProduct() {
        // given
        Product product = new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"), "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN);
        InfoProductDto expected = new InfoProductDto(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),"Книга", "Интересная", BigDecimal.valueOf(18.25));

        // when
        InfoProductDto actual = productMapper.toInfoProductDto(product);


        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.price, expected.getPrice());
    }

    @Test
    void mergeShouldReturnNewProductFromInfoProductDtoAndProduct() {
        // given
        Product product = new Product(UUID.fromString("c74a37d4-0af5-41f1-a8ad-5462bbb7d18b"), "Журнал", "Молодежный",
                BigDecimal.valueOf(10.25), LocalDateTime.MIN);
        ProductDto dto = new ProductDto("Книга", "Интересная", BigDecimal.valueOf(18.25));

        Product expected = Product.builder()
                .uuid(UUID.fromString("c74a37d4-0af5-41f1-a8ad-5462bbb7d18b"))
                .name("Книга")
                .description("Интересная")
                .price(BigDecimal.valueOf(18.25))
                .created(LocalDateTime.MIN)
                .build();

        // when
        Product actual = productMapper.merge(product,dto);

        // then
        assertEquals(expected, actual);
    }
}