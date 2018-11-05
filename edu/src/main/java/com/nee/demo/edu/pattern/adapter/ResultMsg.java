package com.nee.demo.edu.pattern.adapter;

import lombok.Data;

@Data
public class ResultMsg {
    private String code;
    private String msg;
    private Object data;
}
