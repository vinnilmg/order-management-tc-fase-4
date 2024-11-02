package com.fiap.techchallenge4.product.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

@Slf4j
public class ProductReader<Product> implements ItemReader<Product> {

    List<Product> productList;

    public ProductReader(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public Product read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
       /* productList.*/
        return null;
    }
}
