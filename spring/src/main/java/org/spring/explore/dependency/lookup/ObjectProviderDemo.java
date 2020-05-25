package org.spring.explore.dependency.lookup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 通过 {@link ObjectProvider} 进行依赖查找
 *
 * @author mason
 */
@Slf4j
public class ObjectProviderDemo {

    @Bean
    @Primary
    public String helloWorld() {
        return "hello world";
    }

    @Bean
    public String message() {
        return "Message";
    }

    public static void showBean(ApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        log.info(objectProvider.getObject());
    }
}
