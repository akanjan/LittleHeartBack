package com.ecom.repositories;

import com.ecom.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    @Query("SELECT COUNT(u) FROM Category u")
    Long countCategory();
}
