package dev.aquashdw.client.model;

public class ActuatorLoggerDto {
    private String configuredLevel;

    public ActuatorLoggerDto() {
    }

    public ActuatorLoggerDto(String configuredLevel) {
        this.configuredLevel = configuredLevel;
    }

    public String getConfiguredLevel() {
        return configuredLevel;
    }

    public void setConfiguredLevel(String configuredLevel) {
        this.configuredLevel = configuredLevel;
    }

    @Override
    public String toString() {
        return "ActuatorLoggerDto{" +
                "configuredLevel='" + configuredLevel + '\'' +
                '}';
    }
}
