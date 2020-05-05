package org.spring.explore.common.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.spring.explore.common.enums.City;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 用户类
 *
 * @author mason
 */
@Slf4j
@Data
public class User implements BeanNameAware {

    private Long id;

    private String name;

    private City city;

    private City[] workCities;

    private List<City> lifeCities;

    private Resource configFileLocation;

    /**
     * 当前 Bean 的名称
     */
    private transient String beanName;

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("小马哥");
        return user;
    }

    @PostConstruct
    public void init() {
        log.info("User Bean [{}] init...", beanName);
    }

    @PreDestroy
    public void destroy() {
        log.info("User Bean [{}] destroying...", beanName);
    }

}
