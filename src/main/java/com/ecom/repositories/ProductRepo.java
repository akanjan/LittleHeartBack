package com.ecom.repositories;

import com.ecom.entities.Category;
import com.ecom.entities.Product;
import com.ecom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByUser(User user);
    List<Product> findByCategory(Category category);
}
