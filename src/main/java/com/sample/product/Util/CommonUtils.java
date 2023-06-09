package com.sample.product.Util;

import com.sample.product.constants.GlobalConstants;
import com.sample.product.constants.PartOfDay;
import com.sample.product.constants.PerishableProducts;
import com.sample.product.constants.RefreshMode;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.OrderInfo;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.models.StockRefreshRequest;

import java.rmi.UnexpectedException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class CommonUtils {
    private CommonUtils(){}
    private static CommonUtils commonUtilsInstance = null;
    public static synchronized CommonUtils getInstance(){
        if(commonUtilsInstance == null){
            commonUtilsInstance = new CommonUtils();
        }
        return commonUtilsInstance;
    }

    public String getPartOfDay(LocalDateTime time){
        return (time.getHour() >= 12) ? "PM" : "AM";
    }

    public void resetItemAvailabilityIfRequired(LocalDateTime currentDate){
        String actualCurrentDay = currentDate.getDayOfWeek().toString();
        String actualPartOfDay = CommonUtils.getInstance().getPartOfDay(currentDate);
        LocalTime targetTime = LocalTime.of(6, 0);
        if(!Objects.equals(actualCurrentDay, GlobalConstants.currentDay)){
            GlobalConstants.currentDay = actualCurrentDay;
            if(PartOfDay.AM.toString().equals(actualPartOfDay) && currentDate.toLocalTime().isBefore(targetTime)){
                resetItemAvailabilityToDefault();
            }
        }
        if(!Objects.equals(actualPartOfDay, GlobalConstants.partOfDay)){
            GlobalConstants.partOfDay = actualPartOfDay;
            if(PartOfDay.AM.toString().equals(actualPartOfDay) && currentDate.toLocalTime().isBefore(targetTime)){
                resetItemAvailabilityToDefault();
            }
        }
    }

    public void updateCurrentDayInfo(LocalDateTime currentDate){
        String actualCurrentDay = currentDate.getDayOfWeek().toString();
        String actualPartOfDay = CommonUtils.getInstance().getPartOfDay(currentDate);
        if(!Objects.equals(actualCurrentDay, GlobalConstants.currentDay)){
            GlobalConstants.currentDay = actualCurrentDay;
        }
        if(!Objects.equals(actualPartOfDay, GlobalConstants.partOfDay)){
            GlobalConstants.partOfDay = actualPartOfDay;
        }
    }

    public void resetItemAvailabilityToDefault(){
        Stream.of(PerishableProducts.values())
                .forEach(prod -> GlobalConstants.itemAvailabilityMap.put(prod.toString(),100));
    }

    public boolean isItemAvailableForOrder(CreateOrderRequest request){
        AtomicBoolean isItemAvailableForOrder = new AtomicBoolean(true);
        Stream.of(PerishableProducts.values())
                .forEach(prod -> {
                    OrderInfo orderInfo = getOrderInfo(prod.toString(), request.getProductOrders());
                    int itemAvailable = GlobalConstants.itemAvailabilityMap.get(prod.toString());
                    if(orderInfo!=null){
                        if(orderInfo.getOrderQuantity()>itemAvailable){
                            isItemAvailableForOrder.set(false);
                        }
                    }
                });
        return isItemAvailableForOrder.get();
    }

    public void updateMasterOrderDetails(CreateOrderRequest request){
        Stream.of(PerishableProducts.values())
                .forEach(prod -> {
                    OrderInfo orderInfo = getOrderInfo(prod.toString(), request.getProductOrders());
                    String weekDayKey = getWeekDayKey(request.getPurchaseDate());
                    OrderInfo updateOrderInfo = getOrderInfo(prod.toString(), GlobalConstants.productOrderMap.get(weekDayKey));
                    if(updateOrderInfo==null){
                        GlobalConstants.productOrderMap.put(weekDayKey, new ProductOrderDetails());
                        updateOrderInfo = new OrderInfo();
                        updateOrderInfo.setOrderQuantity(getRequestOrderValue(prod.toString(),request.getProductOrders()));
                    }else{
                        updateOrderInfo.setOrderQuantity(updateOrderInfo.getOrderQuantity() + getRequestOrderValue(prod.toString(),request.getProductOrders()));
                    }
                });
    }

    private int getRequestOrderValue(String productType, ProductOrderDetails orderDetails){
        OrderInfo orderInfo = getOrderInfo(productType, orderDetails);
        if(orderInfo!=null){
            return orderInfo.getOrderQuantity();
        }else{
            return 0;
        }
    }

    private String getWeekDayKey(LocalDateTime date){
        String weekDayKey = date.format(GlobalConstants.formatter);
        if (weekDayKey.length() >= 6) {
            String replacedString = weekDayKey.substring(0, weekDayKey.length() - 6) + "000000";
        }
        return weekDayKey;
    }


    public void refreshStock(StockRefreshRequest request) throws UnexpectedException {
        try{
            if(request.getMode().equals(RefreshMode.MANUAL.toString())){
                Stream.of(PerishableProducts.values())
                        .forEach(prod -> {
                            OrderInfo orderInfo = getOrderInfo(prod.toString(), request.getOrderDetails());
                            int itemAvailable = GlobalConstants.itemAvailabilityMap.get(prod.toString());
                            GlobalConstants.itemAvailabilityMap.put(prod.toString(),orderInfo.getOrderQuantity()+itemAvailable);
                        });
            }else if(request.getMode().equals(RefreshMode.DEFAULT.toString())){
                Stream.of(PerishableProducts.values())
                        .forEach(prod -> {
                            if(GlobalConstants.itemAvailabilityMap.get(prod.toString())<20)
                                GlobalConstants.itemAvailabilityMap.put(prod.toString(),100);
                        });
            }
        }catch(Exception e){
            throw new UnexpectedException("Unexpected error occurred while Refreshing stock!");
        }
    }

    public boolean isValidOrderRequest(CreateOrderRequest request){
        return request != null && request.getProductOrders() != null && request.getPurchaseDate() != null;
    }

    public boolean isValidStockRefreshRequest(StockRefreshRequest request){
        boolean isValidStockRefreshReq = true;
        if(request==null || request.getMode()==null || request.getRequestTime() == null){
            isValidStockRefreshReq = false;
        }else if("MANUAL".equals(request.getMode()) && request.getOrderDetails() == null){
            isValidStockRefreshReq = false;
        }else if("MANUAL".equals(request.getMode()) && request.getOrderDetails() != null){
            isValidStockRefreshReq =  isValidOrderDetails(request.getOrderDetails());
        }
        return isValidStockRefreshReq;
    }

    private boolean isValidOrderDetails(ProductOrderDetails orderDetails){
        AtomicBoolean isValidOrderDetails = new AtomicBoolean(false);
        Stream.of(PerishableProducts.values())
                .forEach(prod -> {
                    OrderInfo orderInfo = getOrderInfo(prod.toString(), orderDetails);
                    int itemAvailable = GlobalConstants.itemAvailabilityMap.get(prod.toString());
                    if(orderInfo!=null){
                        isValidOrderDetails.set(itemAvailable >= 20 && itemAvailable <= 50 && (itemAvailable + orderInfo.getOrderQuantity()) <= 100);
                    }
                });
        return isValidOrderDetails.get();
    }

    public OrderInfo getOrderInfo(String productType, ProductOrderDetails orderDetails){
        OrderInfo orderInfo = null;
        if(GlobalConstants.PRODUCT_EGG.equalsIgnoreCase(productType)){
            orderInfo = orderDetails.getEggOrderInfo();
        }else if(GlobalConstants.PRODUCT_FRUIT.equalsIgnoreCase(productType)){
            orderInfo = orderDetails.getFruitsOrderInfo();
        }else if(GlobalConstants.PRODUCT_MEAT.equalsIgnoreCase(productType)){
            orderInfo = orderDetails.getMeatOrderInfo();
        }else if(GlobalConstants.PRODUCT_MILK.equalsIgnoreCase(productType)){
            orderInfo = orderDetails.getMilkOrderInfo();
        }
        return orderInfo;
    }

}
