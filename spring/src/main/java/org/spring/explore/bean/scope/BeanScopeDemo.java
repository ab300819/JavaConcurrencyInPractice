package org.spring.explore.bean.scope;

import lombok.Getter;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * 81. Bean 作用域示例
 *
 * @author mason
 */
@Getter
class BeanScopeDemo implements DisposableBean {

    @Bean
    public static User singleUser() {
        return createUser();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static User prototypeUser() {
        return createUser();
    }

    @Autowired
    @Qualifier("singleUser")
    private User singleUser1;

    @Autowired
    @Qualifier("singleUser")

    private User singleUser2;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser3;

    @Autowired
    private Map<String, User> userMap;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    public static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    /**
     * prototype 对象生命周期不完全受 spring 容器管理，所以不会去销毁，需要自己去实现销毁动作
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        for (Map.Entry<String, User> entry : this.userMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = this.beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.isPrototype()) {
                entry.getValue().destroy();
            }
        }
    }
}