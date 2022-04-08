package dev.aquashdw.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("home")
    public String home(){
        return "index";
    }

    @GetMapping
    public String root(){
        return "redirect:/home";
    }
}
