package com.yyp.id.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MapperScan要指定package 否则mybatis plus会把service多实例化导致注入问题
 */
@MapperScan(basePackages = {"com.yyp.id.generator.dao.mapper"})
@SpringBootApplication
public class IdGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdGeneratorApplication.class, args);
    }

}
