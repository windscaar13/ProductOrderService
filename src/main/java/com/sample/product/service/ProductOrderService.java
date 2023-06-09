package com.sample.product.service;

import com.sample.product.exception.InvalidOrderRequestException;
import com.sample.product.exception.InvalidStockRefreshRequestException;
import com.sample.product.exception.ItemNotAvailableException;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.models.StockRefreshRequest;

import java.rmi.UnexpectedException;
import java.util.Map;

public interface ProductOrderService {

    String refreshStock(StockRefreshRequest request) throws InvalidStockRefreshRequestException, UnexpectedException;
    String placeOrder(CreateOrderRequest request)throws ItemNotAvailableException, InvalidOrderRequestException;
    Map<String, ProductOrderDetails> getWeeklyOrderInfo();

}
