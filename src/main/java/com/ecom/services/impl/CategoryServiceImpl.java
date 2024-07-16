package com.ecom.services.impl;

import com.ecom.entities.Category;
import com.ecom.entities.master_entity.MasSerialPK;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.payloads.CategoryDto;
import com.ecom.payloads.MasSerialDto;
import com.ecom.repositories.CategoryRepo;
import com.ecom.repositories.MasSerialRepo;
import com.ecom.services.CategoryService;
import com.ecom.services.MasSrlService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private MasSerialRepo masSerialRepo;

    @Autowired
    private MasSrlService masSrlService;

    @Autowired
    private ModelMapper modelMapper;

    public Category dtoToCat(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    public CategoryDto catToCatDto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    //Create Category
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCat(categoryDto);
        int srlNo = this.masSrlService.getSrlNo(new MasSerialPK(2024, "CATSRL"));
        category.setCatId(srlNo + 1);
        Category savedCategory = this.categoryRepo.save(category);
        this.masSrlService.updateSrlNo(new MasSerialDto(), new MasSerialPK(2024, "CATSRL"));
        return this.catToCatDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCatTitle(categoryDto.getCatTitle());
        category.setCatDesc(categoryDto.getCatDesc());
        Category UpdateCategory = this.categoryRepo.save(category);
        return this.catToCatDto(UpdateCategory);
    }

    //Delete Category
    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", categoryId));
        this.categoryRepo.delete(category);
    }

    //Get Category
    @Override
    public CategoryDto getSingleCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", categoryId));
        return this.catToCatDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.catToCatDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public Long totalCategory() {
        Long totalCategory = this.categoryRepo.countCategory();
        return totalCategory;
    }

}
