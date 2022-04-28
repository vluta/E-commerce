package com.example.ecommerce.models.DTO;

import com.example.ecommerce.models.entities.Category;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
public class CategoryDTO {

    private String name;
    //private Set<Category> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public Set<Category> getCategories() {
    //    return categories;
    //}

    //public void setCategories(Set<Category> categories) {
    //    this.categories = categories;
    //}
}
