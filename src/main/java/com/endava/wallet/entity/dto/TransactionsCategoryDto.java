package com.endava.wallet.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class TransactionsCategoryDto {
    private final Long id;

    @NotBlank(message = "Please provide a category name")
    @Length(max = 50, message = "Category name is too long")
    private final String category;
    private final Boolean isIncome;
}
