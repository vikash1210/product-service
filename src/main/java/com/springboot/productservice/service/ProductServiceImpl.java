package com.springboot.productservice.service;
import com.springboot.productservice.entity.Product;
import com.springboot.productservice.exception.ProductServiceCustomException;
import com.springboot.productservice.model.ProductRequest;
import com.springboot.productservice.model.ProductResponse;
import com.springboot.productservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");
       Product product =
               Product.builder()
                       .productName(productRequest.getName())
                       .price(productRequest.getPrice())
                       .quantity(productRequest.getQuantity()).build();

        productRepository.save(product);
        log.info("Product Created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Getting product with productId: {}",productId);
      Product product =productRepository
              .findById(productId)
              .orElseThrow(()->new ProductServiceCustomException("Product not found with given Id","PRODUCT_NOT_FOUND"));
      ProductResponse productResponse=new ProductResponse();
        copyProperties(product,productResponse);
        log.info("product response:{}",productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity:{} for product id:{}",quantity,productId);
      Product product= productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product not found for the given id: "+productId,"PRODUCT_NOT_FOUND"));

       if(product.getQuantity()<quantity){
          throw new ProductServiceCustomException("Product not have sufficient quantity","INSUFFICIENT_QUANTITY");
      }
       product.setQuantity(product.getQuantity()-quantity);
       productRepository.save(product);
       log.info("Product Quantity updated successfully");
    }
}
