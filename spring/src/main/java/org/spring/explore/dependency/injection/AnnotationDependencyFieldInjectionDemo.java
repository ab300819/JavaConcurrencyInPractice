package org.spring.explore.dependency.injection;

import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 57. 通过字段注入
 *
 * @author mason
 */
public class AnnotationDependencyFieldInjectionDemo {

    // 会忽略静态字段
    @Autowired
    public UserHolder userHolderByAutowired;

    @Resource
    public UserHolder getUserHolderByResource;

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

}
