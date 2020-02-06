package com.demo.mybatis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

@Slf4j
@Data
@AllArgsConstructor
public class SimpleInterceptor implements Interceptor {

    private String name;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        log.debug("{} intercept start", name);
        return null;
    }

    @Override
    public Object plugin(Object target) {

        log.debug("{} plugin start", name);
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

        log.debug("{} setProperties start", name);
    }
}
