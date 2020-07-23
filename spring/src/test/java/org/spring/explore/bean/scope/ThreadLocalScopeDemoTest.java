package org.spring.explore.bean.scope;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.explore.common.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
class ThreadLocalScopeDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ThreadLocalScopeDemo.class);
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope()));

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    void threadLocalScopeTest() {

        // 不会产生新的 bean
        for (int i = 0; i < 3; i++) {
            User user = applicationContext.getBean(User.class);
            log.info(user.toString());
        }
        for (int i = 0; i < 3; i++) {

            Thread thread = new Thread(() -> {
                User user = applicationContext.getBean(User.class);
                log.info("thread id {} - {}", Thread.currentThread().getId(), user.toString());
            });
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}