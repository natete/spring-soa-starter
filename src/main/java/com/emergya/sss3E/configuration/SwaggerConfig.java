package com.emergya.sss3E.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.emergya.sss3E.commons.props.AppPropsValues;

/**
 * Class that provides the dispatcher servlet application for the Swagger Web MVC context.
 * 
 * @author iiglesias
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private AppPropsValues appPropsValues;

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
