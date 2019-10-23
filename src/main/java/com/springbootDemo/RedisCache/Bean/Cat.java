package com.springbootDemo.RedisCache.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class Cat implements Serializable {

    private static final long serialVersionUID = 8884481585826423719L;
    private String name;
    private String age;
}
