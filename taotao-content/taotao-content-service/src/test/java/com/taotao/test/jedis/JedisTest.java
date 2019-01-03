package com.taotao.test.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	/**
	 * 测试单机版
	 */
	/*@Test
	public void testJedis() {
		//1.创建jedis对象，指定 连接的地址和端口
		Jedis jedis = new Jedis("192.168.1.105", 30001);
		//2.操作redis set
		jedis.set("key123", "kkk123");
		System.out.println(jedis.get("key123"));
		//3.关闭jedis
		jedis.close();
	}
	//使用连接池
	@Test
	public void testJedisPool() throws Exception {
		// 第一步：创建一个JedisPool对象。需要指定服务端的ip及端口。
		JedisPool jedisPool = new JedisPool("192.168.1.105", 6379);
		// 第二步：从JedisPool中获得Jedis对象。
		Jedis jedis = jedisPool.getResource();
		// 第三步：使用Jedis操作redis服务器。
		jedis.set("jedis", "test");
		String result = jedis.get("jedis");
		System.out.println(result);
		// 第四步：操作完毕后关闭jedis对象，连接池回收资源。
		jedis.close();
		// 第五步：关闭JedisPool对象。
		jedisPool.close();
	}*/
	/**
	 * 测试集群版	
	 * 注意：使用docker创建的cluster只对外提供了30001号接口，不对外提供内部redis的ip，因此类似访问单redis访问redis集群30001接口即可
	 */
	/*@Test
	public void testJedisCluster() {
		// 第一步：使用JedisCluster对象。需要一个Set<HostAndPort>参数。Redis节点的列表。
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("172.17.0.2", 6379));
		nodes.add(new HostAndPort("172.17.0.3", 6379));
		nodes.add(new HostAndPort("172.17.0.4", 6379));
		nodes.add(new HostAndPort("172.17.0.5", 6379));
		nodes.add(new HostAndPort("172.17.0.6", 6379));
		nodes.add(new HostAndPort("172.17.0.7", 6379));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 第二步：直接使用JedisCluster对象操作redis。在系统中单例存在。
		jedisCluster.set("hello", "100");
		String result = jedisCluster.get("hello");
		// 第三步：打印结果
		System.out.println(result);
		// 第四步：系统关闭前，关闭JedisCluster对象。
		jedisCluster.close();
	}*/
}
