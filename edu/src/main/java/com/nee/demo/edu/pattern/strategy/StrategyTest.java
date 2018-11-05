package com.nee.demo.edu.pattern.strategy;

public class StrategyTest {

    public static void main(String[] args) {
        Order order = new Order("1", "201811031350", 200);
        System.out.println(order.pay(PayType.ALI_PAY));
    }
}
