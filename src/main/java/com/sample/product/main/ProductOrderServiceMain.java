package com.sample.product.main;

import com.sample.product.exception.InvalidOrderRequestException;
import com.sample.product.exception.InvalidStockRefreshRequestException;
import com.sample.product.exception.ItemNotAvailableException;
import com.sample.product.impl.PerishableOrderServiceImpl;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.models.StockRefreshRequest;
import com.sample.product.service.ProductOrderService;

import java.rmi.UnexpectedException;
import java.util.*;

public class ProductOrderServiceMain {

    ProductOrderService orderService = new PerishableOrderServiceImpl();

    public String refreshStock(StockRefreshRequest request){
        String response;
        try{
            response = orderService.refreshStock(request);
        }catch (UnexpectedException | InvalidStockRefreshRequestException e) {
            return e.getMessage();
        }
        return response;
    }

    public String placeOrder(CreateOrderRequest request){
        String response;
        try{
            response = orderService.placeOrder(request);
        }catch(ItemNotAvailableException | InvalidOrderRequestException e){
            return e.getMessage();
        }
        return response;
    }

    public Map<String,ProductOrderDetails> getWeeklyOrderInfo(){
        return  orderService.getWeeklyOrderInfo();
    }


}
