package com.sample.product.test;
import static org.junit.Assert.assertEquals;

import com.sample.product.constants.GlobalConstants;
import com.sample.product.main.ProductOrderServiceMain;
import com.sample.product.models.CreateOrderRequest;
import com.sample.product.models.ProductOrderDetails;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

public class ProductOrderTest {
    ProductOrderServiceMain serviceMain = new ProductOrderServiceMain();

    //This test method passes an invalid request object and checks for proper exception
    @Test
    public void testPlaceOrderInvalidRequest(){
        CreateOrderRequest order1 = new CreateOrderRequest();
        order1.setProductType(GlobalConstants.PRODUCT_EGG);
        order1.setProductQuantity(-2);
        order1.setPurchaseDate(null);
        String response = serviceMain.placeOrder(order1);
        assertEquals(response, "The Order request is invalid, Please review!");
    }

    //This test method orders all possible product types for both current day and future date. Assertion is done for
    //the item quantity that are saved against each product types.
    @Test
    public void testPlaceOrder(){
        LocalDateTime futureDate = LocalDateTime.of(2023, 6, 8, 10, 30, 0);
        orderForCurrentDay();
        orderForFutureDay(futureDate);
    }

    //This test method orders same product multiple times, but at different time in the same day to check if the
    //item availability gets rest after noon
    @Test
    public void testPlaceOrderByExhaustingItemLimit_Success(){
        LocalDateTime morningOrderTime = LocalDateTime.of(2023, 6, 7, 10, 30, 0);
        CreateOrderRequest order1 = new CreateOrderRequest();
        order1.setProductType(GlobalConstants.PRODUCT_EGG);
        order1.setProductQuantity(54);
        order1.setPurchaseDate(morningOrderTime);
        String dayOfWeek = morningOrderTime.getDayOfWeek().toString();
        String response = serviceMain.placeOrder(order1);
        assertEquals(response, "Order placed Successfully!");

        LocalDateTime eveningOrderTime = LocalDateTime.of(2023, 6, 7, 14, 30, 0);
        CreateOrderRequest order2 = new CreateOrderRequest();
        order2.setProductType(GlobalConstants.PRODUCT_EGG);
        order2.setProductQuantity(55);
        order2.setPurchaseDate(eveningOrderTime);
        dayOfWeek = eveningOrderTime.getDayOfWeek().toString();
        response = serviceMain.placeOrder(order2);
        assertEquals(response, "Order placed Successfully!");
    }

    //This test method orders same product multiple times, but at same time in the day to check if the
    //item availability gets exhausted
    @Test
    public void testPlaceOrderByExhaustingItemLimit_Failure(){
        CreateOrderRequest order1 = new CreateOrderRequest();
        order1.setProductType(GlobalConstants.PRODUCT_MEAT);
        order1.setProductQuantity(54);
        order1.setPurchaseDate(LocalDateTime.now());
        String dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
        String response = serviceMain.placeOrder(order1);
        //assertEquals(54,GlobalConstants.productOrderMap.get(dayOfWeek).getEggOrderInfo().getOrderQuantity());
        assertEquals(response, "Order placed Successfully!");

        CreateOrderRequest order2 = new CreateOrderRequest();
        order2.setProductType(GlobalConstants.PRODUCT_MEAT);
        order2.setProductQuantity(54);
        order2.setPurchaseDate(LocalDateTime.now());
        response = serviceMain.placeOrder(order1);
        assertEquals(response, "Not enough Items available for Ordering. Please try after sometime!");
    }

    //This test method retrieves the entire weekly order and prints it
    @Test
    public void testGetWeeklyOrderInfo(){
        Map<String, ProductOrderDetails> productOrderMap = serviceMain.getWeeklyOrderInfo();
        productOrderMap.entrySet().stream().
                forEach(entry -> System.out.println("Day of Week: "+entry.getKey()+"| "+entry.getValue()));
    }

    private void orderForCurrentDay(){
        CreateOrderRequest order1 = new CreateOrderRequest();
        order1.setProductType(GlobalConstants.PRODUCT_EGG);
        order1.setProductQuantity(54);
        order1.setPurchaseDate(LocalDateTime.now());
        CreateOrderRequest order2 = new CreateOrderRequest();
        order2.setProductType(GlobalConstants.PRODUCT_MEAT);
        order2.setProductQuantity(2);
        order2.setPurchaseDate(LocalDateTime.now());
        CreateOrderRequest order3 = new CreateOrderRequest();
        order3.setProductType(GlobalConstants.PRODUCT_MILK);
        order3.setProductQuantity(3);
        order3.setPurchaseDate(LocalDateTime.now());
        CreateOrderRequest order4 = new CreateOrderRequest();
        order4.setProductType(GlobalConstants.PRODUCT_FRUIT);
        order4.setProductQuantity(10);
        order4.setPurchaseDate(LocalDateTime.now());

        String dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
        serviceMain.placeOrder(order1);
        assertEquals(109,GlobalConstants.productOrderMap.get(dayOfWeek).getEggOrderInfo().getOrderQuantity());
        serviceMain.placeOrder(order2);
        assertEquals(56,GlobalConstants.productOrderMap.get(dayOfWeek).getMeatOrderInfo().getOrderQuantity());
        serviceMain.placeOrder(order3);
        assertEquals(3,GlobalConstants.productOrderMap.get(dayOfWeek).getMilkOrderInfo().getOrderQuantity());
        serviceMain.placeOrder(order4);
        assertEquals(10,GlobalConstants.productOrderMap.get(dayOfWeek).getFruitsOrderInfo().getOrderQuantity());
    }

    private void orderForFutureDay(LocalDateTime futureDate){
        CreateOrderRequest order1 = new CreateOrderRequest();
        order1.setProductType(GlobalConstants.PRODUCT_EGG);
        order1.setProductQuantity(60);
        order1.setPurchaseDate(futureDate);
        CreateOrderRequest order2 = new CreateOrderRequest();
        order2.setProductType(GlobalConstants.PRODUCT_MEAT);
        order2.setProductQuantity(5);
        order2.setPurchaseDate(futureDate);
        CreateOrderRequest order3 = new CreateOrderRequest();
        order3.setProductType(GlobalConstants.PRODUCT_MILK);
        order3.setProductQuantity(4);
        order3.setPurchaseDate(futureDate);
        CreateOrderRequest order4 = new CreateOrderRequest();
        order4.setProductType(GlobalConstants.PRODUCT_FRUIT);
        order4.setProductQuantity(12);
        order4.setPurchaseDate(futureDate);

        String dayOfWeek = futureDate.getDayOfWeek().toString();
        serviceMain.placeOrder(order1);
        assertEquals(60,GlobalConstants.productOrderMap.get(dayOfWeek).getEggOrderInfo().getOrderQuantity());
        serviceMain.placeOrder(order2);
        assertEquals(5,GlobalConstants.productOrderMap.get(dayOfWeek).getMeatOrderInfo().getOrderQuantity());
        serviceMain.placeOrder(order3);
        assertEquals(4,GlobalConstants.productOrderMap.get(dayOfWeek).getMilkOrderInfo().getOrderQuantity());
        serviceMain.placeOrder(order4);
        assertEquals(12,GlobalConstants.productOrderMap.get(dayOfWeek).getFruitsOrderInfo().getOrderQuantity());
    }


}
