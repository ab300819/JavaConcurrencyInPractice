package com.exercise.demo.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestArg {

    boolean success() default true;

    String message() default "Hello";

    int num() default 0;


}
