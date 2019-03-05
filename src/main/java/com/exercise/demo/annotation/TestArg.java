package com.exercise.demo.annotation;

import java.lang.annotation.*;

/**
 * 实验注解参数处理
 * @author mason
 */
@Documented
@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestArg {

    boolean success() default true;

    String message() default "Hello";

    int num() default 0;


}
