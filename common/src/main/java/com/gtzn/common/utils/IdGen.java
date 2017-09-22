/**
 * 
 */
package com.gtzn.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

import com.gtzn.common.sequence.SequenceGen;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author gtzn
 * @version 2013-01-15
 */
public class IdGen {

	
	
	private static SecureRandom random = new SecureRandom();
	
	static SequenceGen sequenceGen = new SequenceGen();
	
	static IdWorker idWorker = new IdWorker();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static long nextLongId() {
		return idWorker.nextId();
	}
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	/**
	 * 返回10位16进制，递增1 ，左补0的rfid序列号
	 * @return
	 */
	public static String rfid() {
		return StringUtils.leftPad(Long.toHexString(sequenceGen.hincrby()).toUpperCase(), 10, "0");
	}
	
	
	public static void initRfid(long value) {
		sequenceGen.hset(value);
	}
	
	//static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	public static void main(String[] args) {
		System.out.println(IdGen.nextLongId());
		/*System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
		System.out.println(new IdGen().getNextId());
		for (int i=0; i<1000; i++){
			System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
		}*/
		//final int a = 0;
		/*for (int i=0; i<10000; i++){
			
			cachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(rfid());
					
				}
			});
			
		}*/
	}
	
	
	

}
