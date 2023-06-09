package com.sample.product.test;
import static org.junit.Assert.assertEquals;

import com.sample.product.constants.GlobalConstants;
import com.sample.product.main.ProductOrderServiceMain;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.OrderInfo;
import com.sample.product.models.ProductOrderDetails;
import com.sample.product.models.StockRefreshRequest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

public class ProductOrderTest {
    ProductOrderServiceMain serviceMain = new ProductOrderServiceMain();
    @Test
    public void testRefreshStock(){
        StockRefreshRequest request = new StockRefreshRequest();
        request.setRequestTime(LocalDateTime.now());
        ProductOrderDetails orderDetails = new ProductOrderDetails();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderQuantity(35);
        orderDetails.setEggOrderInfo(orderInfo);
        orderDetails.setFruitsOrderInfo(orderInfo);
        orderDetails.setMeatOrderInfo(orderInfo);
        orderDetails.setMilkOrderInfo(orderInfo);
        request.setOrderDetails(orderDetails);
        String responseString = serviceMain.refreshStock(request);
        assertEquals(responseString,"Stock Refreshed Successfully!");
    }

    @Test
    public void testGetWeeklyOrderDetails(){
        Map<String, ProductOrderDetails> productOrderMap = serviceMain.getWeeklyOrderInfo();
        productOrderMap.forEach((key, value) -> System.out.println("Day of Week: " + key + "| " + value));
    }

}
