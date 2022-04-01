package dev.aquashdw.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RestController
public class ConfigRestController {
    @Value("${allowed.user}")
    private String allowedUser;

    @Value("${allowed.hosts}")
    private List<String> allowedHosts;

    @GetMapping(
            value = "/status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ConfigClientDto status() {
        return new ConfigClientDto(allowedUser, allowedHosts);
    }
}
