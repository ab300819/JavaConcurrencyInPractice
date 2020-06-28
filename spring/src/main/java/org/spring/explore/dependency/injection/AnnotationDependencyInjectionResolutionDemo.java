package org.spring.explore.dependency.injection;

import lombok.Data;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;
import java.util.Map;

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
    private Map<String,User> userMap;

}
