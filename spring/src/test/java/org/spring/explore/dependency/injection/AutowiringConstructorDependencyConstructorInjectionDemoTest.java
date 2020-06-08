package org.spring.explore.dependency.injection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 56. 通过 constructor 使用 autowiring 实现依赖构造器注入
 *
 * @author maosn
 */
@Slf4j
public class AutowiringConstructorDependencyConstructorInjectionDemoTest {

    private DefaultListableBeanFactory listableBeanFactory;

    @BeforeEach
    void setUp() {
        listableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(listableBeanFactory);

        String configPath = "classpath:autowiring-dependency-constructor-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(configPath);
    }

    @Test
    void getUserHolderTest() {
        UserHolder userHolder = listableBeanFactory.getBean(UserHolder.class);
        log.info(userHolder.toString());
    }

}
