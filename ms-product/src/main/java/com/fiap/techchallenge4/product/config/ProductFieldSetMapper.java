package com.fiap.techchallenge4.product.config;

import com.fiap.techchallenge4.product.model.Product;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.math.BigDecimal;

public class ProductFieldSetMapper implements FieldSetMapper<Product> {
    @Override
    public Product mapFieldSet(FieldSet fieldSet) throws BindException {
        Product product = new Product();
        product.setName(fieldSet.readString("name"));
        product.setDescription(fieldSet.readString("description"));
        product.setPrice(new BigDecimal(fieldSet.readString("price")));
        product.setStock(fieldSet.readInt("stock"));
        return product;
    }
}
