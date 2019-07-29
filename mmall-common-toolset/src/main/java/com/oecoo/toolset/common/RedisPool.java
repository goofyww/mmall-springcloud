package com.oecoo.toolset.common;

import com.oecoo.toolset.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池
 */
public class RedisPool {

    //jedis连接池
    private static JedisPool pool;

    //最大连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));

    //在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));

    //在jedispool中最小的idle状态(空闲的)的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "20"));

    //在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true,则得到的这个jedis实例肯定是可以用的。
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));

    //在return一个jedis实例的时候，是否要进行验证操作，如果赋值true，则放回jedispool的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

    private static String redisHost = PropertiesUtil.getProperty("redis.host");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));

    static {
        initPool();
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); // 连接耗尽时，是否阻塞，false会抛出异常，true阻塞直到超时。默认为 true
        pool = new JedisPool(config, redisHost, redisPort, 1000 * 2);
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    //----------------------------- 把 Jedis 放 回 连 接 池 ---------------------------------------//

    /**
     * 放回损坏连接到连接池
     *
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    /**
     * 放回连接到连接池
     *
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {

        Jedis jedis = pool.getResource();
        jedis.set("goofykey", "goofyvalue");
        String value = jedis.get("goofykey");
        returnResource(jedis);

        pool.destroy();//临时调用，销毁连接池中所有连接
        System.out.println("value : \t" + value);
        System.out.println("program is end");
    }


}
