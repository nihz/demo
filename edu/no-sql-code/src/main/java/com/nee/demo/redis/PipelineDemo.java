package com.nee.demo.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Pipeline;

import java.util.HashSet;
import java.util.Set;

public class PipelineDemo {

    public static void main(String[] args) {
        HostAndPort hostAndPort = new HostAndPort("192.168.1.5", 26379);
        Set<String> sentinels = new HashSet<>();
        sentinels.add(hostAndPort.toString());

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);
        Jedis jedis = jedisSentinelPool.getResource();
        Pipeline pp = jedis.pipelined();

        
    }
}
