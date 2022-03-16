package dev.aquashdw.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping("/category")
    public String[] getArticles() {
        return new String[]{"Food", "Sports", "Shopping"};
    }
}
