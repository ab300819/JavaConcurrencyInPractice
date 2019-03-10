package com.exercise.demo.parameter;

import com.exercise.demo.annotation.TestArg;
import com.exercise.demo.common.dto.Parameter;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ArgumentParameterResolve implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter( MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TestArg.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {


        TestArg testArg = parameter.getParameterAnnotation(TestArg.class);

        Parameter param = new Parameter();
        param.setSuccess(testArg.success());
        param.setMessage(testArg.message());
        param.setNum(testArg.num());
        return param;
    }
}
