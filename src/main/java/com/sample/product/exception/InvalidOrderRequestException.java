package com.sample.product.exception;

public class InvalidOrderRequestException extends Exception{

    public InvalidOrderRequestException(String message) {
        super(message);
    }
}
