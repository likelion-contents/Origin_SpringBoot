package dev.aquashdw.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CookieCheckFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CookieCheckFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.trace(((HttpServletRequest) request).getContextPath());
        logger.trace(((HttpServletRequest) request).getRequestURL().toString());
        logger.trace(((HttpServletRequest) request).getRequestURI());
        logger.trace(((HttpServletRequest) request).getQueryString());
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                logger.info("cookie - {}: {}", cookie.getName(), cookie.getValue());
            }
        chain.doFilter(request, response);
    }
}
