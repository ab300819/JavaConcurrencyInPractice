package org.spring.explore.dependency.lookup;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.spring.explore.common.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 类型安全依赖查找示例
 *
 * @author mason
 */
@Slf4j
class TypeSafetyDependencyLookupDemoTest {

    private static AnnotationConfigApplicationContext applicationContext;

    @BeforeAll
    static void setUp() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test()
    public void displayBeanFactoryGetBeanTest() {
        assertThrows(BeansException.class, () -> applicationContext.getBean(User.class));
    }

    @Test
    public void displayObjectFactoryGetObjectTest() {
        assertThrows(BeansException.class, () -> {
            ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
            userObjectProvider.getObject();
        });
    }

    @Test
    public void displayObjectProviderIfAvailableTest() {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        userObjectProvider.getIfAvailable();
    }

    @Test
    public void displayListableBeanFactoryGetBeansOfTypeTest() {
        applicationContext.getBeansOfType(User.class);
    }

    @Test
    public void displayObjectProviderStreamOpsTest() {
        ObjectProvider<User> userObjectFactory = applicationContext.getBeanProvider(User.class);
        userObjectFactory.forEach(t -> log.info(t.toString()));
    }

    @AfterAll
    static void tearDown() {
        applicationContext.close();
    }
}
