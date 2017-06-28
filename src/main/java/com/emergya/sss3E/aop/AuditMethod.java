package com.emergya.sss3E.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for auditate a method
 * 
 * @author iiglesias
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditMethod {

    /**
     * Audit level request
     * 
     * @return
     */
    public Level[] level();

    /**
     * Audit level
     * 
     * @author iiglesias
     *
     */
    public enum Level {

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
