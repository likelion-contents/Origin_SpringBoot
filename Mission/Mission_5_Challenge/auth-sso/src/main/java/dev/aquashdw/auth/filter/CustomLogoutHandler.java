package dev.aquashdw.auth.filter;

import dev.aquashdw.auth.infra.LogoutPublisherService;
import dev.aquashdw.auth.infra.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static dev.aquashdw.auth.config.WebSecurityConfig.LIKELION_LOGIN_COOKIE;


@Component
public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {
    private final UserCacheService userCacheService;
    private final LogoutPublisherService logoutPublisherService;

    public CustomLogoutHandler(
            @Autowired UserCacheService userCacheService,
            @Autowired LogoutPublisherService logoutPublisherService
            ) {
        this.userCacheService = userCacheService;
        this.logoutPublisherService = logoutPublisherService;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LIKELION_LOGIN_COOKIE)) {
                    this.userCacheService.removeUserCache(cookie.getValue());
                    this.logoutPublisherService.publishLogoutEvent(cookie.getValue());
                }
            }
        }

        super.onLogoutSuccess(request, response, authentication);
    }
}
