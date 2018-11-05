package com.nee.demo.edu.pattern.strategy;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PayStat {
    private int code;
    private Object data;
    private String msg;

    public PayStat(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
