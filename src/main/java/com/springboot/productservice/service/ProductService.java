package com.springboot.productservice.service;

import com.springboot.productservice.model.ProductRequest;
import com.springboot.productservice.model.ProductResponse;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
