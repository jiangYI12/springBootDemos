package com.springbootDemo.RedisCache.RedisConfigration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.Map;


@Slf4j
public class RedisCacheManagerConfig extends RedisCacheManager {

    private static final String SPLIT_FLAG = "#";
    private static final int CACHE_LENGTH = 2;

    public RedisCacheManagerConfig(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @Override
    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
//        if (StrUtil.isBlank(name) || !name.contains(SPLIT_FLAG)) {
//            return super.createRedisCache(name, cacheConfig);
//        }
//
//        String[] cacheArray = name.split(SPLIT_FLAG);
//        if (cacheArray.length < CACHE_LENGTH) {
//            return super.createRedisCache(name, cacheConfig);
//        }
//
        String[] cacheArray = name.split(SPLIT_FLAG);
        if (cacheArray.length < CACHE_LENGTH) {
            return super.createRedisCache(name, cacheConfig);
        }

        if (cacheConfig != null) {
            long cacheAge = Long.parseLong(cacheArray[1]);
            System.err.println(cacheAge);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(cacheAge));
        }

        return super.createRedisCache(name, cacheConfig);
    }

    @Override
    public Cache getCache(String name) {

        return super.getCache(name);
        //自定义缓存查询时，更新缓存
//        CustomizedRedisCache redisCache= new CustomizedRedisCache(
//                name,
//                this.redisCacheWriter,
//                this.defaultRedisCacheConfiguration,
//                this.redisOperations
//        );
    }

    @Override
    protected RedisCache getMissingCache(String name) {
        return super.getMissingCache(name);
    }

}
