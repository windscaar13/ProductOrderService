package com.sample.product.test;
import static org.junit.Assert.assertEquals;

import com.sample.product.Util.CommonUtils;
import com.sample.product.constants.GlobalConstants;
import com.sample.product.constants.PerishableProducts;
import com.sample.product.constants.RefreshMode;
import com.sample.product.main.ProductOrderServiceMain;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.OrderInfo;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.models.StockRefreshRequest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

public class ProductOrderTest {
    ProductOrderServiceMain serviceMain = new ProductOrderServiceMain();
    CommonUtils utils = CommonUtils.getInstance();
    @Test
    public void testRefreshStock1(){
        LocalDateTime orderTime = getPastDateWithTime("000000",4);
        StockRefreshRequest request = new StockRefreshRequest();
        request.setMode(RefreshMode.DEFAULT.toString());
        request.setRequestTime(orderTime);
        String responseString = serviceMain.refreshStock(request);
        assertEquals(responseString,"Stock Refreshed Successfully!");
    }

    @Test
    public void testPlaceOrder1(){
        LocalDateTime orderTime = getPastDateWithTime("073000",4);
        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setPurchaseDate(orderTime);
        ProductOrderDetails orderDetails = new ProductOrderDetails();
        OrderInfo orderInfo1 = new OrderInfo();OrderInfo orderInfo2 = new OrderInfo();
        OrderInfo orderInfo3 = new OrderInfo();OrderInfo orderInfo4 = new OrderInfo();
        orderInfo1.setOrderQuantity(82);orderInfo2.setOrderQuantity(85);
        orderInfo3.setOrderQuantity(90);orderInfo4.setOrderQuantity(95);
        orderDetails.setEggOrderInfo(orderInfo1);
        orderDetails.setFruitsOrderInfo(orderInfo2);
        orderDetails.setMeatOrderInfo(orderInfo3);
        orderDetails.setMilkOrderInfo(orderInfo4);
        orderRequest.setProductOrders(orderDetails);

        String responseString = serviceMain.placeOrder(orderRequest);
        assertEquals(responseString, "Order placed Successfully!");
    }

    @Test
    public void testRefreshStock2(){
        LocalDateTime orderTime = getPastDateWithTime("134500",4);
        StockRefreshRequest request = new StockRefreshRequest();
        request.setMode(RefreshMode.DEFAULT.toString());
        request.setRequestTime(orderTime);
        System.out.println("Before Stock Refresh!");
        Stream.of(PerishableProducts.values())
                .forEach(prod -> System.out.println("Item value for prod "+prod.toString()+" - "+GlobalConstants.itemAvailabilityMap.get(prod.toString())));
        String responseString = serviceMain.refreshStock(request);
        Stream.of(PerishableProducts.values())
                .forEach(prod -> {
                    ProductOrderDetails orderDetails = GlobalConstants.productOrderMap.get(getDateString(orderTime,"000000"));
                    OrderInfo orderInfo = utils.getOrderInfo(prod.toString(),orderDetails);
                    System.out.println("Order Details of "+prod.toString()+" - "+orderInfo.getOrderQuantity());
                        });
        System.out.println("After Stock Refresh!");
        Stream.of(PerishableProducts.values())
                .forEach(prod -> System.out.println("Item value for prod "+prod.toString()+" - "+GlobalConstants.itemAvailabilityMap.get(prod.toString())));
        assertEquals(responseString,"Stock Refreshed Successfully!");
    }

    @Test
    public void testGetWeeklyOrderDetails(){
        Map<String, ProductOrderDetails> productOrderMap = serviceMain.getWeeklyOrderInfo();
        productOrderMap.forEach((key, value) -> System.out.println("Day of Week: " + key + "| " + value));
    }

    private LocalDateTime getFutureDateWithTime(String timeString, int daysToBeAdded){
        LocalDateTime futureDate = LocalDateTime.now();
        String dateString = getDateString(futureDate,timeString);
        System.out.println(dateString);
        futureDate = LocalDateTime.parse(dateString, GlobalConstants.formatter);
        futureDate = futureDate.plusDays(daysToBeAdded);
        return futureDate;
    }

    private LocalDateTime getPastDateWithTime(String timeString, int daysToBeAdded){
        LocalDateTime pastDate = LocalDateTime.now();
        String dateString = getDateString(pastDate,timeString);
        System.out.println(dateString);
        pastDate = LocalDateTime.parse(dateString, GlobalConstants.formatter);
        pastDate = pastDate.minusDays(daysToBeAdded);
        return pastDate;
    }

    private String getDateString(LocalDateTime date, String updateTime) {
        String weekDayKey = date.format(GlobalConstants.formatter);
        String replacedString = null;
        if (weekDayKey.length() >= 6) {
            replacedString = weekDayKey.substring(0, weekDayKey.length() - 6) + updateTime;
        }
        return replacedString;
    }

}
