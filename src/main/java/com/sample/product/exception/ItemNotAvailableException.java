package com.sample.product.exception;

public class ItemNotAvailableException extends Exception{
    public ItemNotAvailableException(String message) {
        super(message);
    }
}
