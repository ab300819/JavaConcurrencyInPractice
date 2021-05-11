package org.spring.explore.bean.lifecycle;

import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>通过属性文件配置 bean<p/>
 *
 * @author mason
 */
@Slf4j
public class BeanMetaDataConfigurationDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);

        String location = "META-INF/user.properties";
        Resource resource = new ClassPathResource(location);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        int beanCount = reader.loadBeanDefinitions(encodedResource);
        log.info("{} beans has been load.", beanCount);

        User user = (User) beanFactory.getBean("test");
        log.info(user.toString());
    }

}
