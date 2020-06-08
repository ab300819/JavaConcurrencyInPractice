package org.spring.explore.dependency.injection;

import org.spring.explore.common.domain.User;
import org.springframework.context.annotation.Bean;

/**
 * 56. 通过注解配置 bean
 *
 * @author mason
 */
public class AnnotationDependencyConstructorInjectionDemo {

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }
}
