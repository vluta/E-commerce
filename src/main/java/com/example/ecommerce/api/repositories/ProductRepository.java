package com.example.ecommerce.api.repositories;

import com.example.ecommerce.models.entities.Category;
import com.example.ecommerce.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Override
    Optional<Product> findById(UUID uuid);

    @Override
    void deleteById(UUID uuid);

    List<Product> findByCategories_Id(UUID id);

    @Transactional
    @Modifying
    @Query("update Product p set p.categories = ?1 where p.id = ?2")
    void addCategory(Category categories, UUID id);

    //Sort sort = new Sort(Sort.Direction.ASC, "name");
    //Pageable pageable = new PageRequest(0, 5, sort);

    //List<Product> findByNameOrderByPriceAsc(String name, Sort sort);


    //@Query("select p from Product p where p.name = ?1 order by p.price")
    //List<Product> findByNameOrderByPriceAsc(String name, Sort sort);


}
