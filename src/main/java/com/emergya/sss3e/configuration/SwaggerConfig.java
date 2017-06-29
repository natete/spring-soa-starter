package com.emergya.sss3e.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.emergya.sss3e.commons.props.AppPropsValues;

/**
 * Class that provides the dispatcher servlet application for the Swagger Web MVC context.
 * 
 * For access to swagger, you use the follow URLs:
 * 
 *   Swagger UI  : http://localhost:8080/{context-app}/swagger-ui.html
 *   Swagger Docs: http://localhost:8080/{context-app}/v2/api-docs
 * 
 * @author iiglesias
 *
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {
    
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private AppPropsValues appPropsValues;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        // Access to swagger ui
                
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    /**
     * Active the configuration of Swagger through the Docket bean. More info in
     * http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
     * 
     * @return
     */
    @Bean
    public Docket api() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();

        // Custom methods response messages

        docket.useDefaultResponseMessages(false);

        // Custom produces for the response messages

        Set<String> produces = new HashSet<String>();
        produces.add(MediaType.APPLICATION_JSON_VALUE);

        docket.produces(produces);

        // Custom request for the request messages

        Set<String> consumes = new HashSet<String>();
        consumes.add(MediaType.APPLICATION_JSON_VALUE);

        docket.consumes(consumes);

        // Custom Information

        docket.apiInfo(new ApiInfo(appPropsValues.getSwaggerInfoTitle(), appPropsValues.getSwaggerInfoDescription(),
                appPropsValues.getSwaggerInfoVersion(), appPropsValues.getSwaggerInfoTermsOfServiceUrl(), new Contact(
                        appPropsValues.getSwaggerInfoContactName(), appPropsValues.getSwaggerInfoContactUrl(),
                        appPropsValues.getSwaggerInfoContactEmail()), appPropsValues.getSwaggerInfoLicense(),
                appPropsValues.getSwaggerInfoLicenseURL(), new ArrayList<VendorExtension>()));
        
        return docket;
    }
}
