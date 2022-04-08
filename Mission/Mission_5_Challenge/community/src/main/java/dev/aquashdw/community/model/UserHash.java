package dev.aquashdw.community.model;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserHash {
    private String cookieId;
    private String username;
    private Instant loginTimestamp;

    public UserHash() {
    }

    public UserHash(String cookieId, String username, Instant loginTimestamp) {
        this.cookieId = cookieId;
        this.username = username;
        this.loginTimestamp = loginTimestamp;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(Instant loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    @Override
    public String toString() {
        return "UserHash{" +
                "cookieId='" + cookieId + '\'' +
                ", username='" + username + '\'' +
                ", loginTimestamp=" + loginTimestamp +
                '}';
    }
}
