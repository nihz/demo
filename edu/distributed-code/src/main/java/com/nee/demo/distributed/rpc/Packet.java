package com.nee.demo.distributed.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Packet {

    private String serviceName;
    private String method;
    private Object[] params;
    private int xid;
}
