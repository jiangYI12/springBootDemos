package com.springbootDemo.RedisCache;

import com.springbootDemo.RedisCache.Services.ICatService;
import com.springbootDemo.RedisCache.Bean.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TomcatApplication {

    @Autowired
    ICatService iCatService;

    public static void main(String[] args) {

        new SpringApplicationBuilder(TomcatApplication.class)
                .run(args);
//		SpringApplication.run(TomcatApplication.class, args);

    }


    @RequestMapping("/get")
    @Cacheable(value = "cat#3", cacheManager = "redisCacheManager")
    public Cat getCat() {
        return new Cat().setName("CAT").setAge("11");
    }

    @RequestMapping("/del")
    @CacheEvict(value = "cat")
    public Cat delCat() {
        return iCatService.getCat();
    }
}
