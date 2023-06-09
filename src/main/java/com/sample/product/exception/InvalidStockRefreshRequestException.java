package com.sample.product.exception;

public class InvalidStockRefreshRequestException extends Exception{

    public InvalidStockRefreshRequestException(String message) {
        super(message);
    }
}
