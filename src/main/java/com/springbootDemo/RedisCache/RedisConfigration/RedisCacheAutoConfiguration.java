/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.springbootDemo.RedisCache.RedisConfigration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.Nullable;

/**
 * 扩展redis-cache支持注解cacheName添加超时时间
 *
 * @author L.cm
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})//在加载配置的类之后再加载当前类
//@ConditionalOnBean({RedisConnectionFactory.class})//仅仅在当前上下文中存在某个对象时，才会实例化一个Bean
//@ConditionalOnMissingBean({CacheManager.class})//仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean
@EnableConfigurationProperties(CacheProperties.class)
//将带有@ConfigurationProperties注解的CacheProperties类注入到IOC,CacheProperties 里面定义了缓存全部类型
public class RedisCacheAutoConfiguration {
    private final CacheProperties cacheProperties;

    private final CacheManagerCustomizers customizerInvoker;
    @Nullable
    private final RedisCacheConfiguration redisCacheConfiguration;

    RedisCacheAutoConfiguration(CacheProperties cacheProperties,
                                CacheManagerCustomizers customizerInvoker,
                                ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration) {
        this.cacheProperties = cacheProperties;
        this.customizerInvoker = customizerInvoker;
        this.redisCacheConfiguration = redisCacheConfiguration.getIfAvailable();
    }

//	@Bean
//	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, ResourceLoader resourceLoader) {
//		DefaultRedisCacheWriter redisCacheWriter = new DefaultRedisCacheWriter(connectionFactory);
//		RedisCacheConfiguration cacheConfiguration = this.determineConfiguration(resourceLoader.getClassLoader());
//		List<String> cacheNames = this.cacheProperties.getCacheNames();
//		Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
//		if (!cacheNames.isEmpty()) {
//			Map<String, RedisCacheConfiguration> cacheConfigMap = new LinkedHashMap<>(cacheNames.size());
//			cacheNames.forEach(it -> cacheConfigMap.put(it, cacheConfiguration));
//			initialCaches.putAll(cacheConfigMap);
//		}
//		RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, cacheConfiguration,
//			initialCaches, true);
//		cacheManager.setTransactionAware(false);
//		return this.customizerInvoker.customize(cacheManager);
//	}

    private RedisCacheConfiguration determineConfiguration(ClassLoader classLoader) {
        if (this.redisCacheConfiguration != null) {
            return this.redisCacheConfiguration;
        } else {
            CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
            config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
            if (redisProperties.getTimeToLive() != null) {
                config = config.entryTtl(redisProperties.getTimeToLive());
            }

            if (redisProperties.getKeyPrefix() != null) {
                config = config.prefixKeysWith(redisProperties.getKeyPrefix());
            }

            if (!redisProperties.isCacheNullValues()) {
                config = config.disableCachingNullValues();
            }

            if (!redisProperties.isUseKeyPrefix()) {
                config = config.disableKeyPrefix();
            }

            return config;
        }
    }
}
