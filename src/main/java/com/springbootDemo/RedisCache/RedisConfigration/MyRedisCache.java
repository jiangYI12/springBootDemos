package com.springbootDemo.RedisCache.RedisConfigration;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

//自定义Cache
public class MyRedisCache extends RedisCache {
    /**
     * Create new {@link RedisCache}.
     *
     * @param name        must not be {@literal null}.
     * @param cacheWriter must not be {@literal null}.
     * @param cacheConfig must not be {@literal null}.
     */
    protected MyRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
    }

    @Override
    public ValueWrapper get(Object key) {

        return super.get(key);
    }

//    private static final Logger logger = LoggerFactory.getLogger(CustomizedRedisCache.class);
//
//    private RedisOperations redisOperations;
//
//    private static final Lock REFRESH_CACKE_LOCK=new ReentrantLock();
//
//    private CacheSupport getCacheSupport(){
//        return ApplicationContextHelper.getApplicationContext().getBean(CacheSupport.class);
//    }
//
//
//    public CustomizedRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, RedisOperations redisOperations) {
//        super(name, cacheWriter,cacheConfig);
//        this.redisOperations=redisOperations;
//    }
//
//
//    public ValueWrapper get(final Object key) {
//        ValueWrapper valueWrapper= super.get(key);
//        if(null!=valueWrapper){
//            CacheItemConfig cacheItemConfig=CacheContainer.getCacheItemConfigByCacheName(key.toString());
//            long preLoadTimeSecond=cacheItemConfig.getPreLoadTimeSecond();
//            ;
//            String cacheKey=this.createCacheKey(key);
//            Long ttl= this.redisOperations.getExpire(cacheKey);
//            if(null!=ttl&& ttl<=preLoadTimeSecond){
//                logger.info("key:{} ttl:{} preloadSecondTime:{}",cacheKey,ttl,preLoadTimeSecond);
//                if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
//                    logger.info("do not need to refresh");
//                }
//                else {
//                    ThreadTaskHelper.run(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                REFRESH_CACKE_LOCK.lock();
//                                if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
//                                    logger.info("do not need to refresh");
//                                }
//                                else {
//                                    logger.info("refresh key:{}", cacheKey);
//                                    CustomizedRedisCache.this.getCacheSupport().refreshCacheByKey(CustomizedRedisCache.super.getName(), key.toString());
//                                    ThreadTaskHelper.removeRefreshCacheTask(cacheKey);
//                                }
//
//                            }
//                            finally {
//                                REFRESH_CACKE_LOCK.unlock();
//                            }
//                        }
//                    });
//                }
//            }
//        }
//        return valueWrapper;
//    }
}
