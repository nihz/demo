package com.nee.demo.edu.pattern.strategy;

import lombok.Data;

@Data
public class Order {

    private String uid;
    private String orderId;
    private double amount;

    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    public PayStat pay(PayType payType) {

        return  payType.get().pay(getUid(), getAmount());
    }
}
