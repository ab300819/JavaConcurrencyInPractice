package com.exercise.demo.common.util;

import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdUtil {

    /**
     * header 中 trace id 的 key
     */
    public final static String TRACE_ID_HEADER = "Trace-Id";

    /**
     * MDC 中 trace id 的 key
     */
    public final static String TRACE_ID = "traceId";

    /**
     * 默认 trace id 值
     */
    public final static String DEFAULT_TRACE_ID = "0";

    public static void setTraceIdHeader(String traceIdHeader) {
        MDC.put(TRACE_ID, traceIdHeader);
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
