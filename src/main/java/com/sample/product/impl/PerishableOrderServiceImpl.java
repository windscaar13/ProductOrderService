package com.sample.product.impl;

import com.sample.product.Util.CommonUtils;
import com.sample.product.constants.GlobalConstants;
import com.sample.product.exception.InvalidOrderRequestException;
import com.sample.product.exception.ItemNotAvailableException;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.service.ProductOrderService;

import java.util.Map;

public class PerishableOrderServiceImpl implements ProductOrderService {

    CommonUtils utils = CommonUtils.getInstance();
    @Override
    public String createOrder(CreateOrderRequest request) throws ItemNotAvailableException,InvalidOrderRequestException {
        if(utils.isValidRequest(request)){
            utils.resetItemAvailabilityIfRequired(request.getPurchaseDate());
            if(utils.isItemAvailableForOrder(request.getProductType(),request.getProductQuantity())){
                utils.updateOrderInformation(request.getPurchaseDate(),request.getProductQuantity(),request.getProductType());
                return "Order placed Successfully!";
            }else{
                throw new ItemNotAvailableException("Not enough Items available for Ordering. Please try after sometime!");
            }
        }else{
            throw new InvalidOrderRequestException("The Order request is invalid, Please review!");
        }
    }

    @Override
    public Map<String, ProductOrderDetails> getWeeklyOrderInfo() {
        return GlobalConstants.productOrderMap;
    }
}
