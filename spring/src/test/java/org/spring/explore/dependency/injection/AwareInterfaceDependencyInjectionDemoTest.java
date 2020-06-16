package org.spring.explore.dependency.injection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 59. 通过 {@link org.springframework.beans.factory.Aware} 接口回调的依赖注入示例
 *
 * @author mason
 */
@Slf4j
class AwareInterfaceDependencyInjectionDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AwareInterfaceDependencyInjectionDemo.class);

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    void getAwareTest() {
        assertEquals(applicationContext.getBeanFactory(), AwareInterfaceDependencyInjectionDemo.beanFactory);
        assertEquals(applicationContext, AwareInterfaceDependencyInjectionDemo.applicationContext);
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}