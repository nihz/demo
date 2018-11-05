package com.nee.demo.edu.pattern.strategy;

public class WechatPay implements Payment {
    @Override
    public PayStat pay(String uid, double amount) {

        return new PayStat(1, "200811031341", "支付成功");
    }
}
