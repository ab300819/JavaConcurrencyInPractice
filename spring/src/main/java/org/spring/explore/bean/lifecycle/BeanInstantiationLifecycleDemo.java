package org.spring.explore.bean.lifecycle;

import org.spring.explore.common.domain.SuperUser;
import org.spring.explore.common.domain.User;
import org.spring.explore.dependency.injection.UserHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Bean 实例前阶段</p>
 *
 * @author mason
 */
@Slf4j
public class BeanInstantiationLifecycleDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();
        listableBeanFactory.addBeanPostProcessor(new CustomInstantiation());
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(listableBeanFactory);
        String[] locations = {"classpath:dependency-lookup-context.xml", "autowiring-dependency-constructor-injection.xml"};
        int loadBean = reader.loadBeanDefinitions(locations);
        log.info("load bean {}", loadBean);

        User user = listableBeanFactory.getBean(User.class);
        log.info(user.toString());

        SuperUser superUser = listableBeanFactory.getBean(SuperUser.class);
        log.info(superUser.toString());

        UserHolder userHolder = listableBeanFactory.getBean(UserHolder.class);
        log.info(userHolder.toString());
    }

    public static class CustomInstantiation implements InstantiationAwareBeanPostProcessor {
        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
            if (ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)) {

                return new SuperUser();
            }
            return null;
        }
    }
}
