package com.lawencon.jobportal.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final PagingRequestArgumentResolver pagingRequestArgumentResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }

    public WebConfig(PagingRequestArgumentResolver pagingRequestArgumentResolver) {
        this.pagingRequestArgumentResolver = pagingRequestArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(
            @SuppressWarnings("null") List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pagingRequestArgumentResolver);
    }
}
