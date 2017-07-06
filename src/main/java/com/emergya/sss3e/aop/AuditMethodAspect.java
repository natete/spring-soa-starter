package com.emergya.sss3e.aop;

import com.emergya.sss3e.aop.AuditMethod.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Aspect for annotation to audit a method
 *
 * @author iiglesias
 */
@Component
@Aspect
public class AuditMethodAspect {

    /**
     * Executes the audit of method around this
     *
     * @param call Joint point for the proceeding method
     * @return
     * @throws Throwable
     */
    @Around("@annotation(AuditMethod)")
    public Object auditMethod(ProceedingJoinPoint call) throws Throwable {

        // Contract of aspect about method executed

        MethodSignature signature = (MethodSignature) call.getSignature();

        // Method over which the annotation is executed

        Method method = signature.getMethod();

        // Access to annotation and attributes

        AuditMethod annotation = method.getAnnotation(AuditMethod.class);

        // Audit level set by annotation attribute

        Level[] level = annotation.level();

        for (int l = 0; level != null && l < level.length; l++) {

            switch (level[l]) {

            case NAME:

                logger.info("Name: " + method.getName());

            case PARAMS:

                Parameter[] parameters = method.getParameters();

                for (int p = 0; parameters != null && p < parameters.length; p++) {

                    logger.info("Parameter " + parameters[p].getName() + ": " + parameters[p].toString());
                }

            default:

            }
        }

        return call.proceed();
    }

    private final static Logger logger = LogManager.getLogger(AuditMethodAspect.class);

}
