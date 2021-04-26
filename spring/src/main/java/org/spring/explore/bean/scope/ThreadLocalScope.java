package org.spring.explore.bean.scope;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.Map;

/**
 * 自定义 {@link ThreadLocalScope} 作用域
 *
 * @author mason
 */

public class ThreadLocalScope implements Scope {

    public static final String SCOPE_NAME = "thread-local";

    private final NamedThreadLocal<Map<String, Object>> threadLocal = new NamedThreadLocal<Map<String, Object>>("thread-local-scope") {
        @Override
        protected Map<String, Object> initialValue() {
            return Maps.newHashMap();
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {

        Map<String, Object> context = getContext();

        Object object = context.get(name);
        if (object == null) {
            object = objectFactory.getObject();
            context.put(name, object);
        }

        return object;
    }

    private Map<String, Object> getContext() {
        return threadLocal.get();
    }

    @Override
    public Object remove(String name) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {

        return String.valueOf(Thread.currentThread().getId());
    }
}
