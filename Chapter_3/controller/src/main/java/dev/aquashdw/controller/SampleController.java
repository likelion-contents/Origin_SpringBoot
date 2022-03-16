package dev.aquashdw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("view")
public class SampleController {


    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/profile")
    public String profile(){
        logger.info("in profile");

        return "profile.html";
    }

    @GetMapping("/sample-profile")
    public String sampleProfile(){
        logger.info("in sample profile");
        return "sample/profile.html";
    }

    @GetMapping(
            value = "/sample-payload",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody SamplePayload samplePayload(){
        return new SamplePayload(
                "aquashdw",
                10,
                "Developer, Lecturer"
        );
    }

    @GetMapping(
            "/sample-jsp"
    )
    public String sampleJsp(Model model){
        logger.info("in sample jsp");
        List<SamplePayload> profiles = new ArrayList<>();
        profiles.add(new SamplePayload("Adam", 22, "Student"));
        profiles.add(new SamplePayload("Bradley", 29, "Accountant"));
        profiles.add(new SamplePayload("Charlie", 35, "Developer"));

        model.addAttribute("profiles", profiles);
        return "view-jsp";
    }

    @GetMapping(
            "/sample-thyme"
    )
    public String sampleThyme(Model model){
        logger.info("in sample thyme");
        List<SamplePayload> profiles = new ArrayList<>();
        profiles.add(new SamplePayload("Adam", 22, "Student"));
        profiles.add(new SamplePayload("Bradley", 29, "Accountant"));
        profiles.add(new SamplePayload("Charlie", 35, "Developer"));

        model.addAttribute("profiles", profiles);
        return "thyme-view";
    }

    @GetMapping(
            "/post-thyme"
    )
    public String postThyme(Model model){
        logger.info("in post thyme");
        Map<String, String> payload = new HashMap<>();
        payload.put("title", "test");
        payload.put("content", "<h2>안녕하세요</h2>");
        payload.put("writer", "aquashdw");
        model.addAttribute("post", payload);
        return "thyme-post";
    }
}
