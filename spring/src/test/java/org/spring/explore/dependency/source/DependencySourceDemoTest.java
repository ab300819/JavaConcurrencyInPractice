package org.spring.explore.dependency.source;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;


class DependencySourceDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DependencySourceDemo.class);

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    public void beanEqualTest() {
        DependencySourceDemo dependencySourceDemo = applicationContext.getBean(DependencySourceDemo.class);
        assertNotSame(dependencySourceDemo.getBeanFactory(), applicationContext);
        assertSame(dependencySourceDemo.getBeanFactory(), dependencySourceDemo.getApplicationContext().getAutowireCapableBeanFactory());
        assertSame(dependencySourceDemo.getResourceLoader(), dependencySourceDemo.getApplicationContext());
        assertSame(dependencySourceDemo.getApplicationEventPublisher(), dependencySourceDemo.getApplicationContext());
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}