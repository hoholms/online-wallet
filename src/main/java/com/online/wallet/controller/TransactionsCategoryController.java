package com.online.wallet.controller;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.online.wallet.model.TransactionsCategory;
import com.online.wallet.model.dto.TransactionsCategoryDto;
import com.online.wallet.service.TransactionsCategoryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class TransactionsCategoryController {

  private static final Logger                      logger =
      LoggerFactory.getLogger(TransactionsCategoryController.class);
  private final        TransactionsCategoryService transactionsCategoryService;

  @GetMapping
  public String getCategories(Model model,
      @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
    setModel(model, pageable);

    logger.info("Retrieved all categories ordered by income status");
    return "categoryList";
  }

  @PostMapping
  public String addCategory(@Valid TransactionsCategoryDto categoryDto, BindingResult bindingResult, Model model,
      @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
      model.mergeAttributes(errorsMap);
      model.addAttribute("categoryDto", categoryDto);
      logger.error("Category add error: {}", errorsMap);
    } else {
      transactionsCategoryService.addCategory(categoryDto);
      logger.info("Category \"{}\" added.", categoryDto.getCategory());
    }

    setModel(model, pageable);
    return "categoryList";
  }

  @GetMapping("{categoryID}")
  public String categoryEditForm(@PathVariable Long categoryID, Model model) {
    model.addAttribute("category", transactionsCategoryService.findById(categoryID));
    logger.info("Editing category with id {}", categoryID);
    return "categoryEdit";
  }

  @PostMapping("/edit")
  public String updateCategory(@RequestParam Long id, @Valid TransactionsCategoryDto categoryDto,
      BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {
      Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
      model.mergeAttributes(errorsMap);
      model.addAttribute("category", categoryDto);
      model.addAttribute("category.id", id);
      logger.error("Category update error: {}", errorsMap);
      return "categoryEdit";
    } else {
      transactionsCategoryService.updateCategory(categoryDto);
      logger.info("Category \"{}\" updated.", categoryDto.getCategory());
    }

    return "redirect:/categories";
  }

  @GetMapping("delete/{categoryID}")
  public String deleteCategory(@PathVariable Long categoryID) {
    transactionsCategoryService.deleteCategoryById(categoryID);
    logger.info("Deleted category with id {}", categoryID);
    return "redirect:/categories";
  }

  private void setModel(final Model model,
      @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) final Pageable pageable) {
    final Page<TransactionsCategory> incomeCategories = transactionsCategoryService.findByIsIncome(true, pageable);
    final Page<TransactionsCategory> expenseCategories = transactionsCategoryService.findByIsIncome(false, pageable);

    Page<TransactionsCategory> allCategories = new PageImpl<>(Collections.emptyList(), pageable,
        incomeCategories.getTotalElements() + expenseCategories.getTotalElements());
    model.addAttribute("categories", allCategories);

    model.addAttribute("incomeCategories", incomeCategories);
    model.addAttribute("expenseCategories", expenseCategories);
  }

}
