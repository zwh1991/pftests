package com.api.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class redis {

    public static Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private String redisip;
    private int redisport;
    
    public redis(String ri,int rp) 
    { 
    	this.redisip=ri;
    	this.redisport=rp;
        initialPool(); 
      //  initialShardedPool(); 
     //   shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource(); 

        
        
    } 

    private void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(20); 
        config.setMaxIdle(5); 
        config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
       
        jedisPool = new JedisPool(config,redisip,redisport);
       
    }
     
    public redis(String ri,int rp,String password) 
    { 
    	this.redisip=ri;
    	this.redisport=rp;
        initialPool(); 
      //  initialShardedPool(); 
     //   shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource(); 
        jedis.auth(password);               
    } 
}
