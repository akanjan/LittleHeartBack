package com.ecom.controllers;

import com.ecom.config.AppConstants;
import com.ecom.payloads.ApiResponse;
import com.ecom.payloads.ProductDto;
import com.ecom.payloads.ProductResponse;
import com.ecom.repositories.ProductRepo;
import com.ecom.services.FileService;
import com.ecom.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    //Save Product
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/user/{userId}/category/{catId}/pro")
    public ResponseEntity<ProductDto> savedPost(
            @RequestBody ProductDto productDto,
            @PathVariable Integer userId,
            @PathVariable Integer catId)
    {
        ProductDto saveProduct = this.productService.saveProduct(productDto, userId, catId);
        return new ResponseEntity<ProductDto>(saveProduct, HttpStatus.CREATED);
    }

    //Get All Products
    @GetMapping("/product")
    public ResponseEntity<ProductResponse> getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    )
    {
        ProductResponse productResponse = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }


    //Get Product by product id
    @GetMapping("/product/{prodId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer prodId)
    {
        ProductDto product = this.productService.getById(prodId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    //Update Product
    @PutMapping("/product/{prodId}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductDto productDto,
            @PathVariable Integer prodId)
    {
        ProductDto updateProd = this.productService.updateProduct(productDto, prodId);
        return new ResponseEntity<ProductDto>(updateProd,HttpStatus.OK);
    }

    //Delete Product
    @DeleteMapping("/product/{prodId}")
    public ApiResponse deleteProduct(@PathVariable Integer prodId)
    {
        this.productService.deleteProduct(prodId);
        return new ApiResponse("Product is successfully Deleted !!",true);
    }


    //Get By Category
    @GetMapping("/category/{catId}/product")
    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable Integer catId)
    {
        List<ProductDto> product = this.productService.getProductByCategory(catId);
        return new ResponseEntity<List<ProductDto>>(product,HttpStatus.OK);
    }

    //Get By User
    @GetMapping("/user/{userId}/product")
    public ResponseEntity<List<ProductDto>> getProductByUser(@PathVariable Integer userId)
    {
        List<ProductDto> product = this.productService.getProductByUser(userId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }


    //Product Image Upload
    @PostMapping("/product/image/upload/{prodId}")
    public ResponseEntity<ProductDto> uploadProductImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("prodId") Integer prodId) throws IOException
    {
        ProductDto productDto = this.productService.getById(prodId);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setProdPhoto(fileName);
        ProductDto updateProduct = this.productService.updateProduct(productDto, prodId);
        return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }



    // method to Get files.
    @GetMapping(value="/product/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName")String imageName,
            HttpServletResponse response
    )throws IOException{
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
