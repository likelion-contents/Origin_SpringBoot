package dev.aquashdw.configclient;

import java.util.List;

public class ConfigClientDto {
    private String allowedUser;
    private List<String> allowedHosts;

    public ConfigClientDto(){

    }

    public ConfigClientDto(String allowedUser, List<String> allowedHosts) {
        this.allowedUser = allowedUser;
        this.allowedHosts = allowedHosts;
    }

    public String getAllowedUser() {
        return allowedUser;
    }

    public void setAllowedUser(String allowedUser) {
        this.allowedUser = allowedUser;
    }

    public List<String> getAllowedHosts() {
        return allowedHosts;
    }

    public void setAllowedHosts(List<String> allowedHosts) {
        this.allowedHosts = allowedHosts;
    }

    @Override
    public String toString() {
        return "ConfigClientDto{" +
                "allowedUser='" + allowedUser + '\'' +
                ", allowedHosts=" + allowedHosts +
                '}';
    }
}
