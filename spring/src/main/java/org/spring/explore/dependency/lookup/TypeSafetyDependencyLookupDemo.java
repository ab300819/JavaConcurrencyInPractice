package org.spring.explore.dependency.lookup;

import lombok.extern.slf4j.Slf4j;
import org.spring.explore.common.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 内心安全 依赖查找示例
 *
 * @author mason
 */
@Slf4j
public class TypeSafetyDependencyLookupDemo {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        // 启动应用上下文
        applicationContext.refresh();

        displayBeanFactoryGetBean(applicationContext);
        displayObjectFactoryGetObject(applicationContext);
        // 安全
        displayObjectProviderIfAvailable(applicationContext);
        // 安全
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        // 安全
        displayObjectProviderStreamOps(applicationContext);

        applicationContext.close();
    }

    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectFactory = applicationContext.getBeanProvider(User.class);
        catchException("displayObjectProviderStreamOps", () -> userObjectFactory.forEach(t -> log.info(t.toString())));
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory beanFactory) {
        catchException("displayListableBeanFactoryGetBeansOfType", () -> beanFactory.getBeansOfType(User.class));
    }

    private static void displayObjectProviderIfAvailable(ApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        catchException("displayObjectProviderIfAvailable", userObjectProvider::getIfAvailable);
    }

    private static void displayObjectFactoryGetObject(BeanFactory beanFactory) {
        ObjectProvider<User> userObjectProvider = beanFactory.getBeanProvider(User.class);
        catchException("displayObjectFactoryGetObject", userObjectProvider::getObject);
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory) {

        catchException("displayBeanFactoryGetBean", () -> beanFactory.getBean(User.class));

    }

    public static void catchException(String source, Runnable runnable) {
        log.info("source from {}", source);

        try {
            runnable.run();
        } catch (BeansException e) {
            log.error("source from {} have error {}", source, String.valueOf(e));
        }

    }

}
