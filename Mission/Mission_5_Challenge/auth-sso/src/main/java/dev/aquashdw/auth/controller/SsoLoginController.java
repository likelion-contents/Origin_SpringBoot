package dev.aquashdw.auth.controller;

import dev.aquashdw.auth.infra.UserCacheService;
import dev.aquashdw.auth.model.UserHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static dev.aquashdw.auth.config.WebSecurityConfig.LIKELION_LOGIN_COOKIE;

@Controller
public class SsoLoginController {
    private static final Logger logger = LoggerFactory.getLogger(SsoLoginController.class);
    private final UserCacheService userCacheService;

    public SsoLoginController(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    @GetMapping("request-login")
    public String requestLogin(
            HttpServletRequest request,
            @RequestParam("request_from") String requestFrom
    ){
        String cookieValue = "";
        for (Cookie cookie: request.getCookies()) {

            if (cookie.getName().equals(LIKELION_LOGIN_COOKIE)){
                cookieValue = cookie.getValue();
                break;
            }
        }

        return String.format(
                "redirect:%s?likelion_login_cookie=%s", requestFrom, cookieValue);
    }

    @GetMapping("check-user")
    public ResponseEntity<UserHash> checkUser(@RequestParam("cookie-id") String cookieId){
        return ResponseEntity.ok(this.userCacheService.getUserCache(cookieId));
    }
}
