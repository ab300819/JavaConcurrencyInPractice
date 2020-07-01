package org.spring.explore.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义注入注解
 *
 * @author mason
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectedUser {
}
