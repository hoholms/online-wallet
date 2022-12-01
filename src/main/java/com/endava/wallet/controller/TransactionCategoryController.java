package com.endava.wallet.controller;

import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class TransactionCategoryController {
    private final TransactionsCategoryService transactionsCategoryService;

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories", transactionsCategoryService.findAllCategoriesOrderByIsIncome());

        return "categoryList";
    }
}
