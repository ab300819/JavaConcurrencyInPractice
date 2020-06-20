package org.spring.explore.dependency.injection;

import lombok.Data;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 64. {@link org.springframework.beans.factory.ObjectProvider} 延迟依赖注入
 *
 * @author mason
 */
@Data
public class LazyAnnotationDependencyInjectionDemo {

    /**
     * 实时注入
     */
    @Autowired
    private User user;

    /**
     * 延时注入
     */
    @Autowired
    private ObjectProvider<User> userObjectProvider;

    @Autowired
    private ObjectFactory<Set<User>> userObjectFactory;

}
