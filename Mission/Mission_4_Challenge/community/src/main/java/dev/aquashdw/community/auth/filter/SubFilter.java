package dev.aquashdw.community.auth.filter;

import dev.aquashdw.community.auth.LikelionSsoConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class SubFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SubFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies){
                if (cookie.getName().equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)){
                    logger.info("Login Token Found, {}", cookie.getValue());
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        logger.info("Login Token Missing");
        chain.doFilter(request, response);

    }
}
