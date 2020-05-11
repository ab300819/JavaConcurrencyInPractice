package org.dubbo.service;

import org.dubbo.api.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello() {
        return "Hello world";
    }
}
