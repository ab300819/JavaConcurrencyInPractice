package org.spring.explore.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

/**
 * 55. 基于 api 实现依赖 setter 方法注入
 *
 * @author mason
 */
public class ApiDependencyConstructorInjectionDemo {

    /**
     * 为 {@link UserHolder} 生成 {@link BeanDefinition}
     *
     * @return BeanDefinition
     */
    public static BeanDefinition createUserHolderBeanDefinition() {
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        definitionBuilder.addConstructorArgReference("superUser");
        return definitionBuilder.getBeanDefinition();
    }
}
