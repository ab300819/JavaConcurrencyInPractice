package org.spring.explore.bean.scope;

import lombok.Data;
import org.spring.explore.common.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * 85. 自定义 ThreadLocal 作用域
 *
 * @author mason
 */
@Data
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public User user() {
        User user = new User();
        user.setId(System.currentTimeMillis());
        return user;
    }
}
