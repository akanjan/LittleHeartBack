package com.ecom.controllers;

import com.ecom.payloads.ApiResponse;
import com.ecom.payloads.CategoryDto;
import com.ecom.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //create Category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto, @PathVariable("userId") Integer uid)
    {
        categoryDto.setUserId(uid);
        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }

    //Get Category
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryBYId(@PathVariable Integer catId)
    {
        return ResponseEntity.ok(this.categoryService.getSingleCategory(catId));
    }

    //Get All Category
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory()
    {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    //Delete Category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
    {
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully",true),
                HttpStatus.OK);
    }

    //Update Category
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer catId)
    {
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
        return ResponseEntity.ok(updateCategory);
    }

    //Total Category Count
    @GetMapping("/totalcategory")
    public Long totalCategory()
    {
        Long totalCategory = this.categoryService.totalCategory();
        return totalCategory;
    }

}
