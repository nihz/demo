package com.nee.demo.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class JedisDemo {

    public static void main(String args[]) {
        HostAndPort hostAndPort = new HostAndPort("192.168.1.5", 26379);
        Set<String> sentinels = new HashSet<>();
        sentinels.add(hostAndPort.toString());

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);
        Jedis jedis = jedisSentinelPool.getResource();
        // jedis.set("name", "nee");
        System.out.println(jedis.get("name"));

        //JedisCluster jedisCluster = new JedisCluster(hostAndPort);
        //jedisCluster.set("", "");

        Config config = new Config();
        config.useSentinelServers().setMasterName("mymaster").addSentinelAddress();

        RedissonClient redissonClient = Redisson.create(config);
        Object nee = redissonClient.getBucket("nee").get();
        System.out.println(nee);
        redissonClient.getList("").add("");
    }
}
