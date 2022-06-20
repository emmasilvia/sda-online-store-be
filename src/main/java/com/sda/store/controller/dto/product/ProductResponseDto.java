package com.sda.store.controller.dto.product;

public class ProductResponseDto extends ProductRequestDto{

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private Long id;
    private String categoryName;


}
