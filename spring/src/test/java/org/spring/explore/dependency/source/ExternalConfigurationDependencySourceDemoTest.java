package org.spring.explore.dependency.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
class ExternalConfigurationDependencySourceDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class, "hello world");
        });

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    public void test() {
        ExternalConfigurationDependencySourceDemo externalConfigurationDependency = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);
        log.info(externalConfigurationDependency.getValue());
        log.info(externalConfigurationDependency.getResource().toString());
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}