package com.exercise.demo.common.filter;

import com.exercise.demo.common.util.TraceIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "traceIdFilter")
@Order(1)
public class TraceIdFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        initTraceId((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void initTraceId(HttpServletRequest request) {
        String traceId = request.getParameter(TraceIdUtil.TRACE_ID);

        if (StringUtils.isBlank(traceId) || TraceIdUtil.defaultTraceId(traceId)) {
            traceId = TraceIdUtil.generateTraceId();
        }

        TraceIdUtil.setTraceId(traceId);
    }
}
