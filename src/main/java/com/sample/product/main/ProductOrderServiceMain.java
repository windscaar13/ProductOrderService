package com.sample.product.main;

import com.sample.product.constants.PerishableProducts;
import com.sample.product.exception.InvalidOrderRequestException;
import com.sample.product.exception.ItemNotAvailableException;
import com.sample.product.impl.PerishableOrderServiceImpl;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.service.ProductOrderService;

import java.util.*;
import java.util.stream.Stream;

public class ProductOrderServiceMain {

    ProductOrderService orderService = new PerishableOrderServiceImpl();

    public String placeOrder(CreateOrderRequest request){
        String response = null;
        try{
            response = orderService.createOrder(request);
        }catch(ItemNotAvailableException | InvalidOrderRequestException e){
            e.printStackTrace();
            return e.getMessage();
        }
        return response;
    }

    public Map<String,ProductOrderDetails> getWeeklyOrderInfo(){
        return  orderService.getWeeklyOrderInfo();
    }

}
