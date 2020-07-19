package com.exercise.demo.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdUtil {

    public final static String TRACE_ID = "trace-id";

    public final static String DEFAULT_TRACE_ID = "0";

    public static void setTraceId(String traceId) {
        traceId = StringUtils.isBlank(traceId) ? DEFAULT_TRACE_ID : traceId;
        MDC.put(TRACE_ID, traceId);
    }

    public static String getTraceId() {

        String traceId = MDC.get(TRACE_ID);
        return StringUtils.isBlank(traceId) ? DEFAULT_TRACE_ID : traceId;
    }

    public static boolean defaultTraceId(String traceId) {
        return DEFAULT_TRACE_ID.equals(traceId);
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

}
