package com.nee.demo.edu.pattern.strategy;

public interface Payment {


    PayStat pay(String uid, double amount);
}
