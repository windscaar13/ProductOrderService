package com.sample.product.Util;

import com.sample.product.constants.GlobalConstants;
import com.sample.product.constants.PerishableProducts;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.OrderInfo;
import com.sample.product.models.ProductOrderDetails;

import java.time.LocalDateTime;
import java.util.Map;
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
        if(actualCurrentDay!= GlobalConstants.currentDay){
            GlobalConstants.currentDay = actualCurrentDay;
            resetItemAvailabilityToDefault();
        }
        if(actualPartOfDay!= GlobalConstants.partOfDay){
            GlobalConstants.partOfDay = actualPartOfDay;
            resetItemAvailabilityToDefault();
        }
    }

    public boolean isItemAvailableForOrder(String productType, int requiredQuantity){
        int availableQuantity = GlobalConstants.itemAvailabilityMap.get(productType);
        if(availableQuantity>=requiredQuantity){
            return true;
        }
        return false;
    }

    public void updateOrderInformation(LocalDateTime purchaseDate, int purchaseQuantity, String productType){
        Map<String, ProductOrderDetails> productOrderMap = GlobalConstants.productOrderMap;
        int availableItemQuantity = GlobalConstants.itemAvailabilityMap.get(productType);
        ProductOrderDetails orderDetails = productOrderMap.get(purchaseDate.getDayOfWeek().toString());
        OrderInfo orderInfo = getOrderInfo(productType,orderDetails);
        orderInfo.setOrderQuantity(orderInfo.getOrderQuantity() + purchaseQuantity);
        //The available Item Quantity changes after the Order gets placed
        availableItemQuantity -= purchaseQuantity;
        orderInfo.setAvailableQuantity(availableItemQuantity);
        //Update the Item Availability Map with the updated available value
        GlobalConstants.itemAvailabilityMap.put(productType,availableItemQuantity);
    }

    public boolean isValidRequest(CreateOrderRequest request){
        if(request.getProductQuantity()<=0 || request.getProductType()==null || request.getPurchaseDate()==null){
            return false;
        }else{
            return true;
        }
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

    public void resetItemAvailabilityToDefault(){
        Stream.of(PerishableProducts.values())
                .forEach(prod -> GlobalConstants.itemAvailabilityMap.put(prod.toString(),100));
    }

}
