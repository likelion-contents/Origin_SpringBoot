package dev.aquashdw.auth.infra;

public interface LogoutPublisherService {
    void publishLogoutEvent(String cookieValue);
}
