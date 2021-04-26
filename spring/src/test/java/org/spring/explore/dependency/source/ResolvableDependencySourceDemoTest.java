package org.spring.explore.dependency.source;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ResolvableDependencySourceDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ResolvableDependencySourceDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class, "hello world");
        });

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    public void test() {

    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}