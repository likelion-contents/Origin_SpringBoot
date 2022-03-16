package dev.aquashdw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("rest")
public class SampleRestController {
    private static final Logger logger = LoggerFactory.getLogger(SampleRestController.class);

    @GetMapping(
            value = "sample-payload"
    )
    public SamplePayload samplePayload(){
        return new SamplePayload(
                "aquashdw",
                10,
                "Developer, Lecturer"
        );
    }

    @GetMapping(
            value = "sample-image",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] sampleImage() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/static/spring_boot_icon.png");

        return inputStream.readAllBytes();
    }
}
