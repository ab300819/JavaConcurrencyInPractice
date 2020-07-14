package org.spring.explore.bean.scope;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.explore.common.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
class BeanScopeDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemo.class);

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    public void beanScopeTest() {
        for (int i = 0; i < 5; i++) {
            User singletonUser = applicationContext.getBean("singleUser", User.class);
            log.info("singleUser: " + singletonUser.toString());

            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            log.info("prototypeUser: " + prototypeUser.toString());
        }

        BeanScopeDemo beanScopeDemo = applicationContext.getBean(BeanScopeDemo.class);

        // single bean 无论依赖注入还是依赖查找都是同一个对象
        log.info(beanScopeDemo.getSingleUser1().toString());
        log.info(beanScopeDemo.getSingleUser2().toString());

        // prototype bean 无论依赖注入还是依赖查找都是新对象
        log.info(beanScopeDemo.getPrototypeUser1().toString());
        log.info(beanScopeDemo.getPrototypeUser2().toString());
        log.info(beanScopeDemo.getPrototypeUser3().toString());

    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }

}