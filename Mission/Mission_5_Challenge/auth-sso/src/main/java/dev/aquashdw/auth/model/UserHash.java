package dev.aquashdw.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@RedisHash("user_cache")
public class UserHash {
    @Id
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
