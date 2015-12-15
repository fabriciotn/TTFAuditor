package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CharsetFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
 
        String browser = req.getHeader("USER-AGENT");
        boolean ieBrowser = browser != null
                && browser.toUpperCase().contains("MSIE");
        boolean hasEncode = req.getCharacterEncoding() != null;
 
        if (ieBrowser && !hasEncode)
            req.setCharacterEncoding("ISO-8859-1");
 
        chain.doFilter(req, response);
    }
 
    @Override
    public void init(FilterConfig arg) throws ServletException {
    }
 
    @Override
    public void destroy() {
    }
}