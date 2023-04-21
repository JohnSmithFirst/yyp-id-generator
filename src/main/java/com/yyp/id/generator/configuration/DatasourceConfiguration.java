package com.yyp.id.generator.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatasourceConfiguration {
    private final DataSource dataSource;

    @PostConstruct
    private void init(){
        log.info("dataSource:{}",dataSource.getClass());
    }

}
