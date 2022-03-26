package dev.aquashdw.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SsoLoginController {
    @GetMapping("request-login")
    public String requestLogin(
            HttpServletRequest request,
            @RequestParam("request_from") String requestFrom
    ){
        String cookieValue = "";
        for (Cookie cookie: request.getCookies()) {

            if (cookie.getName().equals("likelion_login_cookie")){
                cookieValue = cookie.getValue();
                break;
            }
        }

        return String.format(
                "redirect:%s?likelion_login_cookie=%s", requestFrom, cookieValue);
    }
}
