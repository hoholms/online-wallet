package com.endava.wallet.controller;

import com.endava.wallet.entity.dto.TransactionsCategoryDto;
import com.endava.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class TransactionsCategoryController {
    private final TransactionsCategoryService transactionsCategoryService;

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories", transactionsCategoryService.findAllCategoriesOrderByIsIncome());

        return "categoryList";
    }

    @GetMapping("{categoryID}")
    public String categoryEditForm(@PathVariable Long categoryID, Model model) {
        model.addAttribute("category", transactionsCategoryService.findById(categoryID));

        return "categoryEdit";
    }

    @PostMapping
    public String updateCategory(TransactionsCategoryDto categoryDto) {
        transactionsCategoryService.updateCategory(categoryDto);

        return "redirect:/categories";
    }

    @GetMapping("delete/{categoryID}")
    public String addCategory(@PathVariable Long categoryID) {
        transactionsCategoryService.deleteCategoryById(categoryID);

        return "redirect:/categories";
    }
}
