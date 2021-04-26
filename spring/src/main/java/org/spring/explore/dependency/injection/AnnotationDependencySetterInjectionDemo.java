package org.spring.explore.dependency.injection;

import org.spring.explore.common.domain.User;
import org.springframework.context.annotation.Bean;

/**
 * 55. 通过注解配置 bean
 *
 * @author mason
 */
public class AnnotationDependencySetterInjectionDemo {

    @Bean
    public UserHolder userHolder(User user) {
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;
    }
}
