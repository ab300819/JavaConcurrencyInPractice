package org.spring.explore.dependency.injection;

import lombok.Data;
import org.spring.explore.common.annotation.InjectedUser;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * 65. 注解驱动的依赖注入处理过程
 *
 * @author mason
 */
@Data
public class AnnotationDependencyInjectionResolutionDemo {

    /**
     * 依赖查找（处理）
     * DependencyDescriptor ->
     * 必须 (required = true)
     * 实时注入 (eager = true)
     * 通过类型 (User.class)
     * 字段名称 ("user")
     * 是否首要 (primary = true)
     *
     * @see DefaultListableBeanFactory#resolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
     */
    @Autowired
    private User user;

    @Autowired
    private Map<String, User> userMap;

    @InjectedUser
    private User injectUser;

    @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();

        Set<Class<? extends Annotation>> injectAnnotation = new LinkedHashSet<>();
        injectAnnotation.add(Autowired.class);
        injectAnnotation.add(Value.class);
        injectAnnotation.add(InjectedUser.class);
        beanPostProcessor.setAutowiredAnnotationTypes(injectAnnotation);

        return beanPostProcessor;
    }

}
