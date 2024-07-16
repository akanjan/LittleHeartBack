package com.ecom.services;

import com.ecom.payloads.ProductDto;
import com.ecom.payloads.ProductResponse;

import java.util.List;

public interface ProductService {
    //Create
    ProductDto saveProduct(ProductDto productDto, Integer userId, Integer catId);
    //Update
    ProductDto updateProduct(ProductDto productDto, Integer prodId);
    //Delete
    void deleteProduct(Integer pId);
    //Get
    ProductDto getById(Integer prodId);
    //Get All
    ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    //Get All Products By Category
    List<ProductDto> getProductByCategory(Integer catId);
    //Get All Products By User
    List<ProductDto> getProductByUser(Integer userId);
}
