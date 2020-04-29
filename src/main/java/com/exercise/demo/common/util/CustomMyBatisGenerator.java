package com.exercise.demo.common.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis 生成器
 *
 * @author mason
 * @date 2019-04-18
 */
public class CustomMyBatisGenerator {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("generator-config.xml")) {
            ConfigurationParser cp = new ConfigurationParser(warnings);

            Configuration configuration = cp.parseConfiguration(is);
            DefaultShellCallback callback = new DefaultShellCallback(true);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
            myBatisGenerator.generate(null);

            for (String warn : warnings) {
                System.out.print(warn);
            }
        }
    }
}
