package org.spring.explore.dependency.lookup;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mason
 */
@Slf4j
class ObjectProviderDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    public void lookupByObjectProviderTest() {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        log.info(objectProvider.getObject());
    }

    @Test
    public void lookupIfAvailableTest() {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        User user = userObjectProvider.getIfAvailable(User::createUser);
        log.info("当前对象：{}", user.toString());
    }

    @Test
    public void lookupByStreamOpsTest() {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        objectProvider.stream().forEach(log::info);
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}
