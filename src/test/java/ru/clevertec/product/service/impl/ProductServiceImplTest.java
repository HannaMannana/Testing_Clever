package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductMapper mapper;

    @Mock
    private  ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;


    @Test
    void get() {
        // given
        InfoProductDto expected = new InfoProductDto(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                "Книга", "Интересная", BigDecimal.valueOf(18.25));
        Product product = new Product(UUID.fromString("c74a37d4-0af5-41f1-a8ad-5462bbb7d18b"), "Журнал", "Молодежный",
                BigDecimal.valueOf(10.25), LocalDateTime.MIN);
        Optional <Product> prod = Optional.of(product);

        // when
        when(productRepository.findById(product.getUuid()))
                .thenReturn(prod);
        when(mapper.toInfoProductDto(prod.get()))
                .thenReturn(expected);
        InfoProductDto actual = productService.get(product.getUuid());

        // then
        assertThat(actual).isNotNull().isEqualTo(expected);
        verify(productRepository).findById(product.getUuid());
        verify(mapper).toInfoProductDto(product);
    }

    @Test
    void getAll() {
        // given
        InfoProductDto infoProdDto = new InfoProductDto(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                "Книга", "Интересная", BigDecimal.valueOf(18.25));

        List <InfoProductDto> expected = List.of(new InfoProductDto(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                        "Книга", "Интересная", BigDecimal.valueOf(18.25)),
                new InfoProductDto(UUID.fromString("9b77a736-e604-4495-8e55-73e4d56460e4"),
                        "Нига", "Не интересная", BigDecimal.valueOf(18.00)));

        Product product = new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN);

        List<Product> products = List.of(new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                        "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN),
                new Product(UUID.fromString("9b77a736-e604-4495-8e55-73e4d56460e4"),
                        "Нига", "Не интересная", BigDecimal.valueOf(18.00), LocalDateTime.MIN));

        //when
        when(productRepository.findAll())
                .thenReturn(products);
        when(mapper.toInfoProductDto(product))
                .thenReturn(infoProdDto);
        List <InfoProductDto> actual = productService.getAll();

        // then
        assertEquals(expected.size(), actual.size());
        verify(productRepository, atLeastOnce()).findAll();
        verify(mapper).toInfoProductDto(product);
        verify(mapper, atLeastOnce()).toInfoProductDto(any(Product.class));

    }

    @Test
    void createWithUuid() {
        //given
        Product savedProduct = new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                "Книга", "Интересная", BigDecimal.valueOf(18.25),LocalDateTime.MIN);
        Product expected = new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN);
        ProductDto productDto = new ProductDto("Книга", "Интересная", BigDecimal.valueOf(18.25));

        doReturn(expected)
                .when(productRepository).save(savedProduct);
        doReturn(savedProduct)
                .when(mapper).toProduct(productDto);

        //when
        productService.create(productDto);

        //then
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue())
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, savedProduct.getUuid());
    }

    @Test
    void update() {
        Product expected = new Product(UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b"),
                "Книга", "Интересная", BigDecimal.valueOf(18.25), LocalDateTime.MIN);
        ProductDto productDto = new ProductDto("Книга", "Интересная", BigDecimal.valueOf(18.25));

        doReturn(expected)
                .when(productRepository).save(expected);
        doReturn(expected)
                .when(mapper).toProduct(productDto);

        //when
        productService.create(productDto);

        //then
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue())
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid());

    }



    @Test
    void delete() {
        // given
        UUID uuid = UUID.fromString("7bdcf3b6-511c-4ff5-a7f3-bbf03004367b");

        // when
        productService.delete(uuid);

        // then
        verify(productRepository).delete(uuid);

    }
}