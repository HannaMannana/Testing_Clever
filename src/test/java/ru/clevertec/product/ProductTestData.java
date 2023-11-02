package ru.clevertec.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class ProductTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("e38dee2c-d107-43c3-aa8d-e3a9895fa49d");

    @Builder.Default
    private String name = "Harry Potter";

    @Builder.Default
    private String description = "300 pages";

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(30.0);

    @Builder.Default
    private LocalDateTime created = LocalDateTime.of(2000, Month.NOVEMBER, 30, 10, 30);
}
