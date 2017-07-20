package com.emergya.sss3E.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides the dispatcher servlet application for the Rest Web MVC context.
 *
 * @author iiglesias
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = { "com.emergya.sss3E.app" })
public class RestWebConfig {

    /**
     * Configure to plugin JSON as request and response in method handler
     *
     * @return
     */
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        messageConverters.add(mappingJackson2HttpMessageConverter());

        handlerAdapter.setMessageConverters(messageConverters);

        return handlerAdapter;
    }

    /**
     * Configure bean to convert JSON to POJO and vice-versa
     *
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

}
