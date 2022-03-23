package dev.aquashdw.client.controller;

import dev.aquashdw.client.model.CarDto;
import dev.aquashdw.client.service.CarApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("random-data")
public class CarApiController {
    private final CarApiService service;

    public CarApiController(CarApiService service) {
        this.service = service;
    }

    @PostMapping("buy-car")
    public CarDto buyCar(){
        return this.service.buyNewCar();
    }
//
//    @GetMapping("show-cars")
//    public List<CarDto> getCars(){
//        return this.service.getCarsOwned();
//    }
}
