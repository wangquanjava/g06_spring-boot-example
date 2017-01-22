package com.git.util;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	private static final String charSet = "UTF-8";
    private static StringRedisTemplate stringRedisTemplate;
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		RedisUtils.stringRedisTemplate = stringRedisTemplate;
	}
	
	/**
	 * 最基础的添加类
	 * @param key
	 * @param value
	 * @param liveTime 如果没有存活时间，就设置为0
	 */
	public static void put(final byte[] key,final byte[] value,final long liveTime){
		//因为RedisCallback必须有一个泛型，所以种类随意指定了一个Long
		stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				//开启事务
				connection.multi();
				
				
				connection.set(key, value);
				if (liveTime>0) {
					connection.expire(key, liveTime);
				}
				
				//提交事务
				connection.exec();
				return null;
			}
		});
	}
	
	/**
	 * 以字符串保存值
	 * @param key
	 * @param value
	 * @param liveTime
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public static void putString(String key,String value,long liveTime){
		try {
			put(key.getBytes(charSet),value.getBytes(charSet),liveTime);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 把对象序列化保存
	 * @param key
	 * @param value
	 * @param liveTime
	 * @throws Exception 
	 * @throws SerializationException 
	 */
	public static void putObject(String key,Object value,long liveTime) throws SerializationException, Exception{
		RedisSerializer defaultSerializer = stringRedisTemplate.getDefaultSerializer();
		put(key.getBytes(charSet),defaultSerializer.serialize(value), liveTime);
	}
	
	
	/**
	 * 通过key获取值的最基本方法
	 * @param key
	 * @return
	 */
	public static byte[] get(final byte[] key){
		return (byte[]) stringRedisTemplate.execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key);
			}

		});
	}
	public static String getString(String key){
		try {
			return new String(get(key.getBytes(charSet)),charSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static Object getObject(String key){
		try {
			return stringRedisTemplate.getDefaultSerializer().deserialize(get(key.getBytes(charSet)));
		} catch (SerializationException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
