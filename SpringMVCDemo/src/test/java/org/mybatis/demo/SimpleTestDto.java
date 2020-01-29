package org.mybatis.demo;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * mybatis 手动执行
 *
 * @author mason
 */
public class SimpleTestDto {

    @Test
    public void simpleTest() throws SQLException {
//        LogFactory.useLog4JLogging();
        final Configuration configuration = new Configuration();
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        configuration.setAggressiveLazyLoading(true);

//        SimpleInterceptor interceptor1 = new SimpleInterceptor("拦截器1");
//        SimpleInterceptor interceptor2 = new SimpleInterceptor("拦截器2");
//        configuration.addInterceptor(interceptor1);
//        configuration.addInterceptor(interceptor2);

        UnpooledDataSource dataSource = new UnpooledDataSource();
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("110119");
        Transaction transaction = new JdbcTransaction(dataSource, null, false);

        final Executor executor = configuration.newExecutor(transaction);

        StaticSqlSource sqlSource = new StaticSqlSource(configuration, "SELECT * FROM test WHERE id=?");
        List<ParameterMapping> parameterMappings = new ArrayList<>();
        parameterMappings.add(new ParameterMapping.Builder(configuration, "id", Long.class).build());
        ParameterMap.Builder paramBuilder = new ParameterMap
                .Builder(configuration, "defaultParameterMap", TestDto.class, parameterMappings);

        ResultMap resultMap = new ResultMap.Builder(configuration
                , "defaultResultMap"
                , TestDto.class
                , new ArrayList<ResultMapping>() {{
            add(new ResultMapping.Builder(configuration, "id", "id", Long.class).build());
            add(new ResultMapping.Builder(configuration, "username", "username", String.class).build());
            add(new ResultMapping.Builder(configuration, "password", "password", String.class).build());
        }}).build();

        final Cache testDtoCache = new SynchronizedCache(
                new SerializedCache(
                        new LoggingCache(
                                new LruCache(
                                        new PerpetualCache("testDto_cache")
                                ))));
        MappedStatement.Builder msBuilder = new MappedStatement.Builder(configuration, "selectTest", sqlSource, SqlCommandType.SELECT);
        msBuilder.parameterMap(paramBuilder.build());
        List<ResultMap> resultMaps = new ArrayList<>();
        resultMaps.add(resultMap);
        msBuilder.resultMaps(resultMaps);
        msBuilder.cache(testDtoCache);
        MappedStatement ms = msBuilder.build();

        List<TestDto> testDtos = executor.query(ms, 1L, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        assertThat(testDtos, notNullValue());
    }

}
