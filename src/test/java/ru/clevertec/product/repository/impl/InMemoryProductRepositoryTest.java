package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;

    @BeforeEach
    void setup (){
        productRepository = new InMemoryProductRepository();
    }
    @Test
    void findByIdShouldReturnOptionalProduct() {
        // given
        UUID uuid = UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b");
        Product expected = new Product(uuid, "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

        // then
        assertEquals(expected, actual);

    }

    @Test
    void findByIdShouldReturnOptionalEmpty() {
        // given
        UUID uuid = UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367a");

        Optional<Product> expected = Optional.empty();

        // when
        Optional<Product> actual = productRepository.findById(uuid);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfProducts() {
        // given
        List<Product> expected = List.of(new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                        "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN),
                new Product(UUID.fromString("9b77a736-e604-4495-8e55-73e4d56460e4"),
                        "Нига", "Не интересная", BigDecimal.valueOf(18.00), LocalDateTime.MIN));

        // when
        List <Product> actual = productRepository.findAll();

        // then
        assertEquals (expected, actual);
    }

    @Test
    void saveShouldReturnNewProduct() {
        // given
        Product expected = Product.builder()
                .uuid(UUID.fromString("c74a37d4-0af5-41f1-a8ad-5462bbb7d18b"))
                .name("Журнал")
                .description("Молодежный")
                .price(BigDecimal.valueOf(10.25))
                .created(LocalDateTime.MIN)
                .build();

        // when
        Product actual = productRepository.save(expected);

        // then
        assertThat(expected.getUuid()).isSameAs(actual.getUuid());
    }

    @Test
    void saveShouldReturnUpdatedProduct() {
        // given
        Product expected = Product.builder()
                .uuid(UUID.fromString("9b77a736-e604-4495-8e55-73e4d56460e4"))
                .name("Стол")
                .description("Высокий")
                .price(BigDecimal.valueOf(10.25))
                .created(LocalDateTime.MIN)
                .build();

        // when
        productRepository.save(expected);
        Product updated = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertEquals(expected, updated);
    }

    @Test
    void saveShouldReturnOptionalEmpty() {
        // given
        Product expected = Product.builder()
                .uuid(UUID.fromString("c74a37d4-0af5-41f1-a8ad-5462bbb7d18b"))
                .name("Журнал")
                .description("Молодежный")
                .price(BigDecimal.valueOf(10.25))
                .created(LocalDateTime.MIN)
                .build();

        // when
        Product actual = productRepository.save(expected);

        // then
        assertNotNull(actual);
    }

    @Test
    void saveShouldReturnException() {
        // given
        Product expected = null;

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {productRepository.save(expected);});
        String expectedMessage = "Product is null";
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void deleteProductByUuid() {
        // given
        UUID uuid = UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367a");
        Optional<Product> expected = Optional.empty();

        // when
        productRepository.delete(uuid);
        Optional<Product> actual = productRepository.findById(uuid);

        // then
        assertEquals(expected, actual);
    }
}