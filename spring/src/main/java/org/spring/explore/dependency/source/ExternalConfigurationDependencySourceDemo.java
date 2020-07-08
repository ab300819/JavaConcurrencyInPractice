package org.spring.explore.dependency.source;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * 77. 外部化配置作为依赖
 *
 * @author mason
 */
@Slf4j
@Getter
@Configuration
@PropertySource("classpath:default.properties")
public class ExternalConfigurationDependencySourceDemo {

    @Value("${id.user:-1}")
    private String value;

    @Value("${id.file:classpath:user-config.properties}")
    private Resource resource;

}
