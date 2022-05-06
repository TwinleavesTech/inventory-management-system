package com.twinleaves.ims.filter;


import com.twinleaves.ims.filter.request.CustomHttpRequestWrapper;
import com.twinleaves.ims.filter.response.CustomHttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
@Order(1)
public class RequestResponseLoggerFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CustomHttpRequestWrapper customHttpRequestWrapper = new CustomHttpRequestWrapper((HttpServletRequest) request);
        CustomHttpServletResponseWrapper customHttpServletResponseWrapper = new CustomHttpServletResponseWrapper((HttpServletResponse) response);

        // logging Request
        log.info("Request URI: {}" , customHttpRequestWrapper.getRequestURI());
        log.info("Request Method: {}", customHttpRequestWrapper.getMethod());
        Iterator<String> headers = customHttpRequestWrapper.getHeaderNames().asIterator();
        String headerName;
        Map<String, String> headersMap = new LinkedHashMap<>();
        while (headers.hasNext()) {
            headerName = headers.next();
            headersMap.put(headerName, customHttpRequestWrapper.getHeader(headerName));
//            log.info("Header - {} : {}", headerName, customHttpRequestWrapper.getHeader(headerName));
        }
        log.info("Request Headers: {}" , headersMap);
        customHttpRequestWrapper.getParameterMap().forEach((k,v) -> {
            log.info("Request Param - {} : {}", k, v);
        });
        log.info("Request Body: {}", new String(customHttpRequestWrapper.getByteArray()));

        chain.doFilter(customHttpRequestWrapper, customHttpServletResponseWrapper);

        // Logging Response
        log.info("Response status - {}", customHttpServletResponseWrapper.getStatus());
        log.info("Response body - {}", new String(customHttpServletResponseWrapper.getByteArrayOutputStream().toByteArray()));
    }

}
