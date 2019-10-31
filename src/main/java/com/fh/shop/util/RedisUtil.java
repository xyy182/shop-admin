package com.fh.shop.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void set(String key,String value){

        Jedis getresource = null;
        try {
            getresource = RedisPool.getresource();
            getresource.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
           throw new RuntimeException(e.getMessage());
        } finally {
            if(null!=getresource){
                getresource.close();
            }
        }
    }

    public static void del (String key){
        Jedis getresource = null;

        try {
            getresource=RedisPool.getresource();
            getresource.del(key);

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        } finally {

            if(null != getresource){
                getresource.close();
            }
        }

    }

    public static void delbatch (String... key){
        Jedis getresource = null;

        try {
            getresource=RedisPool.getresource();
            getresource.del(key);

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        } finally {

            if(null != getresource){
                getresource.close();
            }
        }

    }

    public static String get (String key){
        Jedis getresource=null;
        String s=null;
        try {
            getresource = RedisPool.getresource();
             s= getresource.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != getresource){
                getresource.close();
            }
        }
        return s;
    }

    public  static  void setEx(String key,int secounds,String value) {

        Jedis getresource=null;
        try {
            getresource = RedisPool.getresource();
            getresource.setex(key,secounds,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != getresource){
                getresource.close();
            }
        }
    }

    public  static  void exPire(String key,int secounds) {

        Jedis getresource=null;
        try {
            getresource = RedisPool.getresource();
            getresource.expire(key,secounds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if(null != getresource){
                getresource.close();
            }
        }
    }
}
