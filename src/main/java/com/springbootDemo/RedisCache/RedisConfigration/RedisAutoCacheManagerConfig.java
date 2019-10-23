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
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * CacheManagerCustomizers配置
 *
 * @author L.cm
 */
@Configuration
@ConditionalOnMissingBean(CacheManagerCustomizers.class)//如已有CacheManagerCustomizers 则不注入
public class RedisAutoCacheManagerConfig {

    /**
     * cacheManagerCustomizers 调用上下文可获得的 CacheManagerCustomizer 交给CacheManager
     * <p>
     * Callback interface that can be implemented by beans wishing to customize the cache manager before it is fully initialized
     * , in particular to tune its configuration.
     * <p>
     * CacheManagerCustomizer
     * 回调接口，可以由希望在完全初始化缓存管理器之前自定义该管理器的bean实现，特别是为了优化其配置
     */
    @Bean
    public CacheManagerCustomizers cacheManagerCustomizers(
            ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
        return new CacheManagerCustomizers(customizers.getIfAvailable());
    }
    //有时候Spring Boot自动给我们配置的RedisCacheManager也不能满足我们应用的需求，
    // 我看到很多用法都直接声明了一个自己的RedisCacheManager，
    // 其实使用CacheManagerCustomizer可以对自动配置的RedisCacheManager进行定制化：
    // @Bean
    //    public CacheManagerCustomizer<RedisCacheManager> cacheManagerCustomizer() {
    //        return new CacheManagerCustomizer<RedisCacheManager>() {
    //            @Override
    //            public void customize(RedisCacheManager cacheManager) {
    //                cacheManager.setUsePrefix(true); //事实上这是Spring Boot的默认设置，为了避免key冲突
    //                Map<String, Long> expires = new HashMap<>();
    //                expires.put("myLittleCache", 12L*60*60);  // 设置过期时间 key is cache-name
    //                expires.put("myBiggerCache", 24L*60*60);
    //                cacheManager.setExpires(expires);  // expire per cache
    //                cacheManager.setDefaultExpiration(24*60*60);// 默认过期时间：24 hours
    //            }
    //        };
    //    }
    //
}
