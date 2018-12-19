package com.nee.demo.distributed.rpc;

import java.util.List;

public abstract class RpcAbstractLoadBalance implements RpcLoadBalance{


    @Override
    public String loadBalance(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            throw new RuntimeException("No available reference");
        }
        if (urls.size() == 1) {
            return urls.get(0);
        }
        return selectOne(urls);
    }

    abstract String selectOne(List<String> urls);

}
