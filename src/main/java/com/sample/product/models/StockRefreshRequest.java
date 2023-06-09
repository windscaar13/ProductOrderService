package com.sample.product.models;

import java.time.LocalDateTime;

public class StockRefreshRequest {

    private String mode;
    private ProductOrderDetails orderDetails;
    private LocalDateTime requestTime;

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ProductOrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ProductOrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

}
