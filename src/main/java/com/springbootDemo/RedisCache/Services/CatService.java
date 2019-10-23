package com.springbootDemo.RedisCache.Services;

import com.springbootDemo.RedisCache.Bean.Cat;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CatService implements ICatService {

    @Cacheable(value = "cat")
    public Cat getCat() {
        return new Cat().setAge("15").setName("Tom");
    }
}
