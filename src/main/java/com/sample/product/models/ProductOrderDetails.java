package com.sample.product.models;

public class ProductOrderDetails {

    private OrderInfo milkOrderInfo;
    private OrderInfo eggOrderInfo;
    private OrderInfo meatOrderInfo;
    private OrderInfo fruitsOrderInfo;

    public OrderInfo getMilkOrderInfo() {
        return milkOrderInfo;
    }

    public void setMilkOrderInfo(OrderInfo milkOrderInfo) {
        this.milkOrderInfo = milkOrderInfo;
    }

    public OrderInfo getEggOrderInfo() {
        return eggOrderInfo;
    }

    public void setEggOrderInfo(OrderInfo eggOrderInfo) {
        this.eggOrderInfo = eggOrderInfo;
    }

    public OrderInfo getMeatOrderInfo() {
        return meatOrderInfo;
    }

    public void setMeatOrderInfo(OrderInfo meatOrderInfo) {
        this.meatOrderInfo = meatOrderInfo;
    }

    public OrderInfo getFruitsOrderInfo() {
        return fruitsOrderInfo;
    }

    public void setFruitsOrderInfo(OrderInfo fruitsOrderInfo) {
        this.fruitsOrderInfo = fruitsOrderInfo;
    }

    @Override
    public String toString() {
        return "ProductOrderDetails{" +
                "milkOrderInfo=" + milkOrderInfo +
                ", eggOrderInfo=" + eggOrderInfo +
                ", meatOrderInfo=" + meatOrderInfo +
                ", fruitsOrderInfo=" + fruitsOrderInfo +
                '}';
    }
}
