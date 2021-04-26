package org.spring.explore.dependency.injection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 56. 通过注解进行构造器注入
 *
 * @author mason
 */
@Slf4j
class AnnotationDependencyFieldInjectionDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyFieldInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    void getUserHolderTest() {
        AnnotationDependencyFieldInjectionDemo userHolder = applicationContext.getBean(AnnotationDependencyFieldInjectionDemo.class);
        log.info(userHolder.userHolderByAutowired.toString());
        log.info(userHolder.getUserHolderByResource.toString());

    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}