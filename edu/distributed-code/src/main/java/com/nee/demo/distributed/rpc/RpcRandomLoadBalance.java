package com.nee.demo.distributed.rpc;

import java.util.List;
import java.util.Random;

public class RpcRandomLoadBalance extends RpcAbstractLoadBalance {

    private final Random random = new Random();

    @Override
    String selectOne(List<String> urls) {

        return urls.get(random.nextInt(urls.size()));
    }

}
