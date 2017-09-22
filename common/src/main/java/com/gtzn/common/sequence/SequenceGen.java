package com.gtzn.common.sequence;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.gtzn.common.config.Global;
import com.gtzn.common.utils.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SequenceGen {
	
	private static String id = Global.getConfig("redis.sequence.keyPrefix");
	private static String sequence = "sequence";
	private static JedisPool pool;

	public SequenceGen() {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(Integer.parseInt(Global.getConfig("redis.maxIdle")));
		config.setMaxTotal(Integer.parseInt(Global.getConfig("redis.maxTotal")));
		pool = new JedisPool(config, StringUtils.trim(Global.getConfig("redis.host")));
	}

	private Long execute(RedisCallback callback) {
		Jedis jedis = pool.getResource();
		try {
			return callback.doWithRedis(jedis);
		} finally {
			jedis.close();
		}
	}

	public void hset(final long value) {
		execute(new RedisCallback() {
			@Override
			public Long doWithRedis(Jedis jedis) {
				jedis.hset(id, sequence, Long.toString(value));
				return null;
			}
		});
	}

	public Long hincrby() {
		return execute(new RedisCallback() {
			@Override
			public Long doWithRedis(Jedis jedis) {
				return jedis.hincrBy(id, sequence, 1);
			}
		});
	}

}
