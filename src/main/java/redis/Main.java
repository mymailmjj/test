package redis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

/**
 * jedis 操作redis集合
 * 
 * 
 * 
 * @author mymai
 *
 */
public class Main extends TestCase {

	private Jedis jedis = null;

	@Override
	protected void setUp() throws Exception {
		/*
		 * jedis = new Jedis("106.12.18.166", 6379); jedis.connect();
		 */
		super.setUp();
	}

	public void testJedis() {

		String string = jedis.get("a");

		System.out.println(string);

		for (int i = 0; i < 10; i++) {
			jedis.zadd("zaa", i, "member" + i);
		}

		Set<String> zrange = jedis.zrange("zaa", 4, 8);

		Iterator<String> iterator = zrange.iterator();

		while (iterator.hasNext()) {
			String next = iterator.next();
			System.out.println("next:" + next);
		}

	}

	@Override
	protected void tearDown() throws Exception {
		// jedis.disconnect();
		super.tearDown();
	}

	public void testSentinel() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		String masterName = "mymaster";
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("106.12.18.166:26379");
		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig);
		HostAndPort currentHostMaster = jedisSentinelPool.getCurrentHostMaster();
		System.out.println(currentHostMaster.getHost() + "--" + currentHostMaster.getPort());// 获取主节点的信息
		Jedis resource = jedisSentinelPool.getResource();
		String value = resource.get("a");
		System.out.println(value);// 获得键a对应的value值
		resource.close();

	}

	public void testRedisCluster() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		HostAndPort hostAndPort = new HostAndPort("192.168.1.99", 7000);
		HostAndPort hostAndPort1 = new HostAndPort("192.168.1.99", 7001);
		HostAndPort hostAndPort2 = new HostAndPort("192.168.1.99", 7002);
		HostAndPort hostAndPort3 = new HostAndPort("192.168.1.99", 7003);
		HostAndPort hostAndPort4 = new HostAndPort("192.168.1.99", 7004);
		HostAndPort hostAndPort5 = new HostAndPort("192.168.1.99", 7005);
		nodes.add(hostAndPort);
		nodes.add(hostAndPort1);
		nodes.add(hostAndPort2);
		nodes.add(hostAndPort3);
		nodes.add(hostAndPort4);
		nodes.add(hostAndPort5);
		JedisCluster jedisCluster = new JedisCluster(nodes, poolConfig);// JedisCluster中默认分装好了连接池.
		// redis内部会创建连接池，从连接池中获取连接使用，然后再把连接返回给连接池
		String string = jedisCluster.get("a");
		System.out.println(string);

	}

}
