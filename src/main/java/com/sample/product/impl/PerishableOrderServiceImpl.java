package com.sample.product.impl;

import com.sample.product.Util.CommonUtils;
import com.sample.product.constants.GlobalConstants;
import com.sample.product.constants.PartOfDay;
import com.sample.product.exception.InvalidOrderRequestException;
import com.sample.product.exception.InvalidStockRefreshRequestException;
import com.sample.product.exception.ItemNotAvailableException;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.models.StockRefreshRequest;
import com.sample.product.service.ProductOrderService;

import java.rmi.UnexpectedException;
import java.util.Map;

public class PerishableOrderServiceImpl implements ProductOrderService {

    CommonUtils utils = CommonUtils.getInstance();

    @Override
    public String refreshStock(StockRefreshRequest request) throws InvalidStockRefreshRequestException, UnexpectedException {
        utils.resetItemAvailabilityIfRequired(request.getRequestTime());
        if(utils.isValidStockRefreshRequest(request) && PartOfDay.PM.toString().equals(GlobalConstants.partOfDay)){
            utils.refreshStock(request);
        }else{
            throw new InvalidStockRefreshRequestException("The Stock Refresh request is invalid, Please review!");
        }
        return "Stock Refreshed Successfully!";
    }

    @Override
    public String placeOrder(CreateOrderRequest request) throws ItemNotAvailableException,InvalidOrderRequestException {
        if(utils.isValidOrderRequest(request)){
            utils.updateCurrentDayInfo(request.getPurchaseDate());
            if(utils.isItemAvailableForOrder(request)){
                utils.updateMasterOrderDetails(request);
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
