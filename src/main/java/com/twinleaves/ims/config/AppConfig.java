package com.twinleaves.ims.config;

import com.twinleaves.ims.interceptor.IMSInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    IMSInterceptor imsInterceptor;

    @Autowired
    public AppConfig(IMSInterceptor imsInterceptor) {
        this.imsInterceptor = imsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(imsInterceptor);
    }
}