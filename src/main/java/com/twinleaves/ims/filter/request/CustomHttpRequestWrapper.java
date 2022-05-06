package com.twinleaves.ims.filter.request;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * CustomHttpRequestWrapper class used to prohibit request object to be closed.
 */
public class CustomHttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] byteArray;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CustomHttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // This will exhaust the request hence loading data into byteArray
        byteArray = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // To return exhausted request using the loaded byteArray
        return new CustomDelegatingServletInputStream(new ByteArrayInputStream(getByteArray()));
    }

    public byte[] getByteArray() {
        return byteArray;
    }
}