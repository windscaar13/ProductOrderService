package com.sample.product.service;

import com.sample.product.exception.InvalidOrderRequestException;
import com.sample.product.exception.ItemNotAvailableException;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.OrderInfo;
import com.sample.product.models.ProductOrderDetails;

import java.util.Map;

public interface ProductOrderService {

    public String createOrder(CreateOrderRequest request)throws ItemNotAvailableException, InvalidOrderRequestException;
    public Map<String, ProductOrderDetails> getWeeklyOrderInfo();

}
