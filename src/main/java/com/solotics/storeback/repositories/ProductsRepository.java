package com.solotics.storeback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solotics.storeback.models.Product;

public interface ProductsRepository extends JpaRepository<Product,Long>{

}
