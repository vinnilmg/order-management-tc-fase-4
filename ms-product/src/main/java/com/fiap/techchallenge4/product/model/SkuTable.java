package com.fiap.techchallenge4.product.model;

import lombok.Data;

import java.util.List;
@Data
public class SkuTable {

    private Long id;

    private List<Product> productList;
}
