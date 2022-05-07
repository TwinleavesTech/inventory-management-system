package com.twinleaves.ims.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@PropertySource("classpath:/query-catalog/ims-query-catalog.xml")
@Configuration
public class IMSQueryCatalog {

    @Value("${inventory-filter-query}")
    private String imsFilterQuery;

}
