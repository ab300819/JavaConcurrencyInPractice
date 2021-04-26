package org.spring.explore.dependency.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 76. 理解 ResolvableDependency
 *
 * @author mason
 */
@Slf4j
public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void init() {
        log.info(value);
    }

}
