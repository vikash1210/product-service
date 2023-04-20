package com.springboot.productservice.controller;
import com.springboot.productservice.model.ProductRequest;
import com.springboot.productservice.model.ProductResponse;
import com.springboot.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")

public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
      long productId= productService.addProduct(productRequest);
      return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('Customer') || hasAuthority('SCOPE_internal')")
    @GetMapping("/{id}")
//http://localhost:8080/product/{id}
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
    ProductResponse productResponse= productService.getProductById(productId);
    return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @PutMapping("/reduceQuantity/{id}")
    //http://localhost:8080/product/reduceQuantity/1?quantity=10
    public ResponseEntity<String> reduceQuantity(@PathVariable("id") long productId,@RequestParam long quantity){
        productService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }
}
