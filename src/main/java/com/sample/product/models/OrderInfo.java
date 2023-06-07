package com.sample.product.models;

public class OrderInfo {

    private int orderQuantity = 0;
    private int availableQuantity = 0;

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderQuantity=" + orderQuantity +
                ", availableQuantity=" + availableQuantity +
                '}';
    }

}
