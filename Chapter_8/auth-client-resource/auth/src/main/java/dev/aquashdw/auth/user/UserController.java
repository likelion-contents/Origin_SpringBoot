package dev.aquashdw.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(
            @Autowired
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(){
        return "index";
    }

    @GetMapping(
            "/user/login"
    )
    public String login(){
        return "user/loginForm";
    }

    @GetMapping(
            "/user/sign-up"
    )
    public String signUp(){
        return "user/signUpForm";
    }

    @PostMapping(
            "/user/sign-up"
    )
    public String createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("passwordCheck") String passwordCheck
    ) {
        if (!password.equals(passwordCheck)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userService.signUp(username, password);

        return "user/loginForm";
    }
}
