package org.spring.explore.bean.lifecycle;

import org.spring.explore.common.domain.SuperUser;
import org.spring.explore.common.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import lombok.extern.slf4j.Slf4j;

/**
 * BeanDefinition 合并
 *
 * @author mason
 */
@Slf4j
public class BeanDefinitionMergeDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(listableBeanFactory);
        int loadBean = reader.loadBeanDefinitions("classpath:dependency-lookup-context.xml");
        log.info("load bean {}", loadBean);

        User user=listableBeanFactory.getBean(User.class);
        log.info(user.toString());

        SuperUser superUser=listableBeanFactory.getBean(SuperUser.class);
        log.info(superUser.toString());

    }

}
