package com.springboot.productservice.exception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductServiceCustomException extends RuntimeException{
    private String errorCode;
    public ProductServiceCustomException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
