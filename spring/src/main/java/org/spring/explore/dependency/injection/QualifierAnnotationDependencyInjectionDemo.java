package org.spring.explore.dependency.injection;

import lombok.Data;
import org.spring.explore.common.annotation.UserGroup;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

/**
 * 63. {@link Qualifier} 注解依赖注入
 *
 * @author mason
 */
@Data
public class QualifierAnnotationDependencyInjectionDemo {

    /**
     * 默认注入有 {@link Primary} 注解的 bean
     */
    @Autowired
    private User primaryUser;

    /**
     * 注入指定名称的 bean
     */
    @Autowired
    @Qualifier("user")
    private User qualifierUser;

    @Autowired
    private Collection<User> allUserList;

    @Autowired
    @Qualifier
    private Collection<User> qualifierUserList;

    @Autowired
    @Qualifier("name")
    private Collection<User> nameUserList;

    @Autowired
    @UserGroup
    private Collection<User> userGroupList;

    /**
     * 这里的 {@link Qualifier} 表示逻辑分组
     *
     * @return {@link User}
     */
    @Bean
    @Qualifier
    public User user1() {
        User user = new User();
        user.setId(1L);
        return user;
    }

    @Bean
    @Qualifier
    public User user2() {
        User user = new User();
        user.setId(2L);
        return user;
    }

    /**
     * 按名称分组
     *
     * @return
     */
    @Bean
    @Qualifier("name")
    public User user3() {
        User user = new User();
        user.setId(3L);
        return user;
    }

    @Bean
    @Qualifier("name")
    public User user4() {
        User user = new User();
        user.setId(4L);
        return user;
    }

    /**
     * 自定义注解进行分组
     *
     * @return
     */
    @Bean
    @UserGroup
    public User user5() {
        User user = new User();
        user.setId(5L);
        return user;
    }

    @Bean
    @UserGroup
    public User user6() {
        User user = new User();
        user.setId(6L);
        return user;
    }

}
