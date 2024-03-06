package com.example.sakila.input;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data


public class FilmInput {
    @NotNull
    @Size(min = 1, max = 45)
    private String title;

    @NotNull
    private Byte languageId;

    @NotNull
    private Byte rentalDuration;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal rentalRate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal replacementCost;

    private Timestamp lastUpdate;

}
