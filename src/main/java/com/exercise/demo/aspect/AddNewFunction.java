package com.exercise.demo.aspect;

import com.exercise.demo.app.service.Food;
import com.exercise.demo.app.service.FoodImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AddNewFunction {
    @DeclareParents(value = "com.exercise.demo.app.service.Person+", defaultImpl = FoodImpl.class)
    public static Food food;
}
