package dev.aquashdw.jpa.config;

import dev.aquashdw.jpa.interceptor.HeaderLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DemoConfiguration implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(DemoConfiguration.class);

    private final HeaderLoggingInterceptor headerLoggingInterceptor;

    public DemoConfiguration(
            @Autowired HeaderLoggingInterceptor headerLoggingInterceptor
    ) {
        this.headerLoggingInterceptor = headerLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(headerLoggingInterceptor)
                .addPathPatterns("/post/**")
                .excludePathPatterns("/except/**");
    }
}
