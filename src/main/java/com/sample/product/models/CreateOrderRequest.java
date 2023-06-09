package com.sample.product.models;

import java.time.LocalDateTime;

public class CreateOrderRequest {

    private ProductOrderDetails productOrders;
    private LocalDateTime purchaseDate;

    public ProductOrderDetails getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(ProductOrderDetails productOrders) {
        this.productOrders = productOrders;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
