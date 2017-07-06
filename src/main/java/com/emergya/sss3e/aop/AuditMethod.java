package com.emergya.sss3e.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for audit a method
 *
 * @author iiglesias
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditMethod {

    /**
     * Audit level request
     *
     * @return
     */
    Level[] level();

    /**
     * Audit level
     *
     * @author iiglesias
     */
    enum Level {

        /**
         * Name of method
         */
        NAME,

        /**
         * Params of method
         */
        PARAMS
    }
}
