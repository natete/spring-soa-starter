package com.emergya.sss3E.app.configuration;

import com.emergya.sss3E.swagger.configuration.SwaggerConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Implementation for the Servlet 3.0+ environments in order to configure the ServletContext programmatically, as
 * opposed to (or possibly in conjunction with) the traditional web.xml-based approach. This initializer will be
 * detected automatically by SpringServletContainerInitializer, which itself is bootstrapped automatically by any
 * Servlet 3.0 container.
 *
 * @author iiglesias
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Specify (@)Configuration and/or (@)Component classes to be provided to the root application context.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    /**
     * Specify (@)Configuration and/or (@)Component classes to be provided to the dispatcher servlet application
     * context.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { RestWebConfig.class, SwaggerConfig.class };
    }

    /**
     * Specify the servlet mapping(s) for the DispatcherServlet — for example "/", "/app", etc.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
