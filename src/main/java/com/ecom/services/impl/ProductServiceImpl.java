package com.ecom.services.impl;

import com.ecom.entities.Category;
import com.ecom.entities.Product;
import com.ecom.entities.User;
import com.ecom.entities.master_entity.MasSerialPK;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.payloads.MasSerialDto;
import com.ecom.payloads.ProductDto;
import com.ecom.payloads.ProductResponse;
import com.ecom.repositories.CategoryRepo;
import com.ecom.repositories.ProductRepo;
import com.ecom.repositories.UserRepo;
import com.ecom.services.MasSrlService;
import com.ecom.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MasSrlService masSrlService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    //Saved Product
    @Override
    public ProductDto saveProduct(ProductDto productDto, Integer userId, Integer catId) {

        Product product = this.modelMapper.map(productDto, Product.class);

        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));

        Category category = this.categoryRepo.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id", catId));


        int srlNo = this.masSrlService.getSrlNo(new MasSerialPK(2024, "PROSRL"));
        product.setProdId(srlNo+1);
        product.setCategory(category);
        product.setUser(user);
        product.setProdPhoto("default.png");
        product.setEntryDate(new Timestamp(new Date().getTime()));
        Product savedProduct = this.productRepo.save(product);
        this.masSrlService.updateSrlNo(new MasSerialDto(), new MasSerialPK(2024, "PROSRL"));
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    //Update Product
    @Override
    public ProductDto updateProduct(@NotNull ProductDto productDto, Integer prodId) {
        Product product = this.productRepo.findById(prodId).orElseThrow(() ->
                new ResourceNotFoundException("Product", "product id", prodId));

        product.setProdTitle(productDto.getProdTitle());
        product.setProdDesc(productDto.getProdDesc());
        product.setProdPhoto(productDto.getProdPhoto());
        product.setProdPrice(productDto.getProdPrice());
        product.setProdDiscount(productDto.getProdDiscount());
        product.setProdQuantity(productDto.getProdQuantity());
        product.setProdActive(productDto.isProdActive());

        Product updatedProduct = this.productRepo.save(product);
        return this.modelMapper.map(updatedProduct,ProductDto.class);
    }

    //Delete Product
    @Override
    public void deleteProduct(Integer pId) {
        Product product = this.productRepo.findById(pId).orElseThrow(() ->
                new ResourceNotFoundException("Product", "Product Id", pId));
        this.productRepo.delete(product);
    }

    //Get product by Id
    @Override
    public ProductDto getById(Integer prodId) {
        Product product = this.productRepo.findById(prodId).orElseThrow(() ->
                new ResourceNotFoundException("Product", "Product Id", prodId));
        return this.modelMapper.map(product,ProductDto.class);
    }

    //Get All Product
    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort= (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Product> pageProduct = this.productRepo.findAll(p);
        List<Product> allProducts = pageProduct.getContent();
        List<ProductDto> productDtos = allProducts.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDtos);
        productResponse.setPageNumber(pageProduct.getNumber());
        productResponse.setPageSize(pageProduct.getSize());
        productResponse.setTotalElements(pageProduct.getTotalElements());
        productResponse.setTotalPages(pageProduct.getTotalPages());
        productResponse.setLastPage(pageProduct.isLast());
        return productResponse  ;
    }

    //Get product By Category
    @Override
    public List<ProductDto> getProductByCategory(Integer catId) {
        Category cat = this.categoryRepo.findById(catId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", catId));
        List<Product> products = this.productRepo.findByCategory(cat);
        List<ProductDto> productDtos = products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        return productDtos;
    }

    //Get Product By User
    @Override
    public List<ProductDto> getProductByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));
        List<Product> products = this.productRepo.findByUser(user);
        List<ProductDto> productDtos = products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        return productDtos;
    }

}
