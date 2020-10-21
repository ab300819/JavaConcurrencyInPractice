package com.common.util;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 递归获取当前类和父类
 *
 * @author mason
 */
public class BeanUtil {

    public static Map<String, String> getAllFields(Object object) {
        Map<String, String> fieldMap = new HashMap<>();
        getFields(fieldMap, object.getClass(), object);
        return fieldMap;
    }

    public static void getFields(Map<String, String> fieldMap, Class<?> clazz, Object object) {
        if (clazz == null) {
            return;
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null) {
                    fieldMap.put(field.getName(), value.toString());
                }
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        getFields(fieldMap, clazz.getSuperclass(), object);
    }

}
