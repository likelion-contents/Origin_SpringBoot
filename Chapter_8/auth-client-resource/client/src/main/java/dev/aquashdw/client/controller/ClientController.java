package dev.aquashdw.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/home")
    public String home(){
        return "client/home";
    }
}
