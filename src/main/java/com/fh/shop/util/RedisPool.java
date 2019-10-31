package com.fh.shop.util;

import redis.clients.jedis.*;

public class RedisPool {

    private RedisPool(){}

    //redis连接池
    private static JedisPool pool;


    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(100);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestOnBorrow(true);
        pool = new JedisPool(jedisPoolConfig, "192.168.138.130", 6379);
    }
    static  {
        initPool();
    }
    public static Jedis getresource(){

        return  pool.getResource();
    }


}
