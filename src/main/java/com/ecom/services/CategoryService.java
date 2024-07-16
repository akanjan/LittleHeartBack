package com.ecom.services;

import com.ecom.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    //Create
    CategoryDto createCategory(CategoryDto categoryDto);

    //Update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //Delete
    void deleteCategory(Integer categoryId);

    //Get single
    CategoryDto getSingleCategory(Integer categoryId);

    //Get All
    List<CategoryDto> getAllCategories();

    Long totalCategory();
}
