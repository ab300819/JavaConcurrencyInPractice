package org.spring.explore.dependency.injection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.explore.common.domain.SuperUser;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 63. {@link Qualifier} 注解依赖注入
 *
 * @author mason
 */
@Slf4j
class QualifierAnnotationDependencyInjectionDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    void getUserTest() {

        QualifierAnnotationDependencyInjectionDemo qualifierAnnotationDependencyInjection
                = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        User user = (User) applicationContext.getBean("user");
        SuperUser superUser = (SuperUser) applicationContext.getBean("superUser");

        assertEquals(user, qualifierAnnotationDependencyInjection.getQualifierUser());
        assertEquals(superUser, qualifierAnnotationDependencyInjection.getPrimaryUser());

        assertEquals(4, qualifierAnnotationDependencyInjection.getQualifierUserList().size());
        assertEquals(2, qualifierAnnotationDependencyInjection.getAllUserList().size());
        assertEquals(2, qualifierAnnotationDependencyInjection.getNameUserList().size());
        assertEquals(2, qualifierAnnotationDependencyInjection.getUserGroupList().size());
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}