/**
 * 
 */
package com.gtzn.common.utils;

import org.apache.ibatis.cache.Cache;
import org.mybatis.caches.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gtzn.common.config.Global;
import com.gtzn.common.utils.StringUtils;

/**
 * Jedis Cache 工具类
 * 
 * @author gtzn
 * @version 2014-6-29
 */
public class JedisUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

	public static final String KEY_PREFIX = Global.getConfig("redis.keyPrefix");

	static Cache cache = new RedisCache(KEY_PREFIX);
	
	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static String get(String key) {
		String value = null;
		try {
			value = (String) cache.getObject(key);
			value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
			logger.debug("get {} = {}", key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return value;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Object getObject(String key) {
		Object value = null;
		try {
			value = cache.getObject(key);
		} catch (Exception e) {
			logger.warn("getObject {} = {}", key, value, e);
		} finally {
		}
		return value;
	}

	public static void putObject(final Object key, final Object value) {
		cache.putObject(key, value);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long del(String key) {
		long result = 0;
		try {
			cache.removeObject(key);
		} catch (Exception e) {
			logger.warn("del {}", key, e);
		} finally {
		}
		return result;
	}
	/**
	 * 清空缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static void clear() {
		try {
			cache.clear();
		} catch (Exception e) {
			logger.warn("clear {}", e);
		} finally {
		}
	}
	
}
