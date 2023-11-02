package ru.clevertec.product.data;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@FieldNameConstants
public class InfoProductDto {

    public InfoProductDto(){

    }

    public InfoProductDto(UUID uuid, String name, String description, BigDecimal price) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Идентификатор не может быть null
     */
    UUID uuid;

    /**
     * Имя продукта смотрите {@link ru.clevertec.product.entity.Product}
     */
    String name;

    /**
     * Описание продукта не может быть null, может быть пустой строкой
     */
    String description;

    /**
     * Стоимость не может быть null или негативным
     */
    BigDecimal price;
}

