package dev.aquashdw.producer.controller;


import dev.aquashdw.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    private final ProducerService producerService;

    public ProducerController(
            @Autowired
                    ProducerService producerService
    ) {
        this.producerService = producerService;
    }

    @GetMapping("/")
    public void sendMessage(){
        producerService.send();
    }
}
