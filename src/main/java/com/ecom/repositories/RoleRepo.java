package com.ecom.repositories;

import com.ecom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
