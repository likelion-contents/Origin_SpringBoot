package dev.aquashdw.client.controller;

import dev.aquashdw.client.service.NcpApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ncp-api")
public class NcpApiController {
    private static final String testIp = "210.57.252.50";
    private final NcpApiService service;

    public NcpApiController(NcpApiService service) {
        this.service = service;
    }

    @GetMapping("geolocation")
    public ResponseEntity<?> getLocationByIp(@RequestParam(value = "ip", defaultValue = testIp) String ip){
        return ResponseEntity.ok(this.service.geoLocation(ip));
    }
}
