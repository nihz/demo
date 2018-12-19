package com.nee.demo.distributed.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class Packet implements Serializable {

    private String serviceName;
    private String method;
    private Class[] paramTypes;
    private Object[] params;

    private int xid;

    private Object result;
}
