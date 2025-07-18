package com.harsahaat.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    private String title;
    private String description;
    private int mrpPrice;
    private int sellingPrice;
    private String color;
    private List<String> images;
    private String category_1;
    private String category_2;
    private String category_3;

    private String sizes;


}
