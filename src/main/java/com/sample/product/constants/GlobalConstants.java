package com.sample.product.constants;

import com.sample.product.Util.CommonUtils;
import com.sample.product.models.OrderInfo;
import com.sample.product.models.ProductOrderDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GlobalConstants {

    public static final String PRODUCT_MILK = "MILK";
    public static final String PRODUCT_EGG = "EGG";
    public static final String PRODUCT_FRUIT = "FRUIT";
    public static final String PRODUCT_MEAT = "MEAT";

    public static String currentDay;
    public static String partOfDay;

    public static Map<String, ProductOrderDetails> productOrderMap;

    public static Map<String, Integer> itemAvailabilityMap;

    static{
        productOrderMap = new HashMap<>();
        Stream.of(DaysOfWeek.values())
                .forEach(day -> {
                    productOrderMap.put(day.toString(),new ProductOrderDetails());
                    productOrderMap.get(day.toString()).setEggOrderInfo(new OrderInfo());
                    productOrderMap.get(day.toString()).setMilkOrderInfo(new OrderInfo());
                    productOrderMap.get(day.toString()).setMeatOrderInfo(new OrderInfo());
                    productOrderMap.get(day.toString()).setFruitsOrderInfo(new OrderInfo());
                });
        itemAvailabilityMap = new HashMap<>();
        Stream.of(PerishableProducts.values())
                .forEach(prod -> GlobalConstants.itemAvailabilityMap.put(prod.toString(),100));
        currentDay = LocalDate.now().getDayOfWeek().toString();
        partOfDay = CommonUtils.getInstance().getPartOfDay(LocalDateTime.now());
    }

}
