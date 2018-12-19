package com.nee.demo.distributed.rpc;

import java.util.List;

public interface RpcLoadBalance {

    String loadBalance(List<String> urls);
}
