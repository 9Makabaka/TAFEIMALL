package cn.edu.xidian.tafei_mall.model.vo;


import lombok.Data;

@Data
public class Product {
    private String name;
    private String description;
    private double price;
    private int stock;

    // Getters and Setters
}