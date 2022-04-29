package com.twinleaves.ims.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Bean(name = "ims-ds")
    @ConfigurationProperties(prefix = "spring.datasource.ims-ds")
    public DataSource empPortalDataSource() {
        return DataSourceBuilder.create().build();
    }
}